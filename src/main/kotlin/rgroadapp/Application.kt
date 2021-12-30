package rgroadapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import rgroad.WorldConfig
import rgroad.WorldGenerator
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

@SpringBootApplication
class RgRoadApplication

@RestController
class WorldResource {
    @GetMapping("/world/{seed}/chunks/{x}/{y}")
    @ResponseBody
    fun createChunk(
        @PathVariable("seed") seed: String,
        @PathVariable("x") x: Int,
        @PathVariable("y") y: Int,
        @RequestParam("s") size: UInt,
        @RequestParam("h") height: Float,
        @RequestParam("r") roughness: Float,
        model: Model
    ): ByteArray {
        // TODO create cached repository
        val world = WorldGenerator(WorldConfig(size, height, roughness), seed)
        world.addChunk(x, y)

        val imageStream = ByteArrayOutputStream()
        ImageIO.write(world.chunks[Pair(x, y)]!!.heightMap, "png", imageStream)
        return imageStream.toByteArray()
    }
}