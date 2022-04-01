package rgroad

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File
import javax.imageio.ImageIO


internal class HeightMapTest {
    companion object {
        private val expectedData = intArrayOf(
            0xb5, 0xb5, 0xb1, 0xa0, 0x87, 0x80, 0x6c, 0x54, 0x42, 0x47, 0x4a, 0x41, 0x38, 0x35, 0x30, 0x26,
            0xac, 0xaf, 0xa9, 0x9a, 0x8d, 0x7c, 0x75, 0x5e, 0x51, 0x53, 0x51, 0x49, 0x44, 0x39, 0x30, 0x2b,
            0xac, 0xa8, 0x97, 0x98, 0x8a, 0x7f, 0x78, 0x6a, 0x52, 0x52, 0x53, 0x48, 0x42, 0x39, 0x34, 0x29,
            0xa0, 0x9a, 0x8e, 0x88, 0x87, 0x82, 0x7f, 0x74, 0x69, 0x65, 0x5e, 0x50, 0x40, 0x38, 0x2e, 0x2d,
            0x8c, 0x8c, 0x80, 0x7d, 0x86, 0x8a, 0x83, 0x83, 0x79, 0x6d, 0x61, 0x56, 0x45, 0x3d, 0x38, 0x2f,
            0x82, 0x81, 0x88, 0x83, 0x81, 0x83, 0x88, 0x89, 0x82, 0x7b, 0x6f, 0x65, 0x53, 0x4e, 0x43, 0x40,
            0x80, 0x81, 0x87, 0x79, 0x7d, 0x88, 0x8e, 0x98, 0x9b, 0x8a, 0x85, 0x7a, 0x66, 0x57, 0x52, 0x4c,
            0x7d, 0x76, 0x7d, 0x78, 0x7d, 0x89, 0x97, 0x99, 0x9c, 0x94, 0x8e, 0x7f, 0x73, 0x62, 0x61, 0x65,
            0x72, 0x6c, 0x70, 0x7d, 0x76, 0x88, 0x91, 0xa0, 0xb3, 0x9b, 0x92, 0x80, 0x6f, 0x72, 0x6f, 0x71,
            0x79, 0x73, 0x79, 0x78, 0x7f, 0x84, 0x96, 0xa1, 0xab, 0xa1, 0x94, 0x8c, 0x80, 0x86, 0x81, 0x8a,
            0x75, 0x70, 0x76, 0x73, 0x82, 0x81, 0x93, 0xa5, 0xab, 0x9e, 0xa1, 0x96, 0x93, 0x93, 0x9e, 0xa4,
            0x62, 0x62, 0x64, 0x72, 0x75, 0x80, 0x90, 0xa0, 0xa8, 0xa2, 0x9d, 0x98, 0x98, 0x9d, 0xa8, 0xb2,
            0x5b, 0x5c, 0x5a, 0x67, 0x77, 0x88, 0x8a, 0x99, 0x9c, 0x9c, 0x9d, 0xa8, 0xab, 0xb0, 0xb4, 0xb9,
            0x52, 0x56, 0x5f, 0x5e, 0x75, 0x88, 0x92, 0x94, 0x9f, 0xab, 0xa8, 0xaf, 0xb0, 0xb9, 0xc1, 0xc7,
            0x48, 0x55, 0x50, 0x58, 0x6d, 0x7a, 0x94, 0xa4, 0xaa, 0xad, 0xad, 0xae, 0xc1, 0xc4, 0xce, 0xd1,
            0x3c, 0x4c, 0x51, 0x57, 0x65, 0x77, 0x8d, 0xa2, 0xa5, 0xb2, 0xb4, 0xb5, 0xb6, 0xc3, 0xd5, 0xdc
        ).map {b -> b.toByte()}.toByteArray()
    }

    private val heightMap = HeightMap(
        1, 2, HeightMapConfig(16u, 1.0f, 0.5f), 4
    )

    @Test
    fun testGenerate() {
        val image = heightMap.generate()
        val data = ByteArray(heightMap.size * heightMap.size)
        image.raster.getDataElements(0, 0, heightMap.size, heightMap.size, data)

        ImageIO.write(image, "png", File("heightMap.png"))

        assertEquals(expectedData, data)
    }
}