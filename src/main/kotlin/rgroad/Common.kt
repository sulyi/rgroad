package rgroad

data class HeightMapConfig(val size: UInt, val height: Float, val roughness: Float)

fun getSafeSeed(seed: String?) : Long {
    return 4L
}