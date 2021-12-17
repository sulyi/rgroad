package rgroad

data class HeightMapConfig(val size: UInt, val height: Float, val roughness: Float)

fun getSafeSeed(seed: Int) : Long {
    return getSafeSeed(seed.toLong())
}

fun getSafeSeed(seed: Long): Long {
    // TODO("Replace dummy")
    return 4L
}

fun getSafeSeed(seed: String?) : Long {
    return getSafeSeed(4L)
}