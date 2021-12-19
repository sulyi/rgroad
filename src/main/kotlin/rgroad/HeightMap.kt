package rgroad

import java.awt.image.BufferedImage
import java.lang.System.arraycopy
import java.math.BigInteger
import java.math.MathContext
import java.security.MessageDigest
import kotlin.math.floor
import kotlin.math.log2

class HeightMap(private val offsetX: Int, private val offsetY: Int, private val config: HeightMapConfig, seed: Long) {

    private val seed: Long = getSafeSeed(seed)
    private val steps: Int = floor(log2(((config.size - 1u).toDouble()))).toInt() + 1
    val size: Int = 1 shl steps

    fun generate(): BufferedImage {
        val cx = offsetX * size
        val cy = offsetY * size

        var subSize = size
        var height = config.height * 127 - 1
        var length = 2

        val data = ByteArray(size * size + 2 * size + 1)
        intArrayOf(0, size, 0, size).zip(
            intArrayOf(0, 0, size, size)
        ).forEach { (x, y) ->
            data[x + y + y * size] = (127 + height * getRandomValue(x + cx, y + cy)).toInt().toByte()
        }

        repeat(steps) {
            var value: Float
            var pixel: Double
            height *= config.roughness
            length += length - 1
            subSize = subSize shr 1

            // square step
            for (i in 1 until length step 2) {
                for (j in 1 until length step 2) {
                    val x = i * subSize
                    val y = j * subSize
                    pixel = intArrayOf(x - subSize, x - subSize, x + subSize, x + subSize).zip(
                        intArrayOf(y - subSize, y + subSize, y - subSize, y + subSize)
                    ).map { (a, b) -> data[a + b + b * size].toInt() and 0xFF }.average()

                    value = getRandomValue(x + cx, y + cy)
                    pixel += height * value
                    data[x + y + y * size] = pixel.toInt().toByte()
                }
            }

            // diamond step
            for (i in 0 until length) {
                for (j in ((i and 1) xor 1) until length step 2) {
                    val x = i * subSize
                    val y = j * subSize
                    pixel = intArrayOf(x - subSize, x, x, x + subSize).zip(
                        intArrayOf(y, y - subSize, y + subSize, y)
                    ).filter { (a, b) -> a in 0..size && b in 0..size }
                        .map { (a, b) -> data[a + b + b * size].toInt() and 0xFF }.average()

                    value = getRandomValue(i * subSize + cx, j * subSize + cy)
                    pixel += height * value
                    data[x + y + y * size] = pixel.toInt().toByte()
                }
            }
        }

        val image = BufferedImage(size + 1, size + 1, BufferedImage.TYPE_BYTE_GRAY)
        image.raster.setDataElements(0, 0, size + 1, size + 1, data)
        return image.getSubimage(0, 0, size, size)
    }

    private fun getRandomValue(x: Int, y: Int): Float {
        // Uniform [-1, 1]
        val bitLength = 64
        val hash = MessageDigest.getInstance("SHA-256")
        val at = ByteArray((bitLength shr 3) + 1)
        val atValue = BigInteger.valueOf((x.toLong() xor (y.toLong() shl (bitLength shr 1))) xor seed).toByteArray()
        arraycopy(atValue, 0, at, at.size - atValue.size, atValue.size)
        val minValue = BigInteger(-1, ByteArray(hash.digestLength) { _ -> (0xFF).toByte() }).toBigDecimal()
        val value = BigInteger(-1, hash.digest(at)).toBigDecimal().divide(minValue, MathContext.DECIMAL64)
        return value.toFloat() * 2 - 1
    }
}