package rgroad

import java.awt.image.BufferedImage

data class HeightMapConfig(val size: UInt, val height: Float, val roughness: Float)
data class WorldChunkData(val offset_x: Int, val offset_y: Int, val heightMap: BufferedImage)
data class WorldConfig(val chunkSize: UInt, val height: Float, val roughness: Float)

val defaultWorldConfig = WorldConfig(256u, 1f, .5f)

fun getSafeSeed(seed: String?): Long {
    return 4L
}
