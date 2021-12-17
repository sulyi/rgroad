package rgroad.world

import rgroad.*

class WorldChunk(coordinates: Pair<Int, Int>, config: WorldConfig, seed: Long) {
    private val x: Int = coordinates.first
    private val y: Int = coordinates.second
    private val seed: Long = getSafeSeed(seed)
    private val heightMap: HeightMap = HeightMap(
        x, y, HeightMapConfig(
            config.chunkSize,
            config.height,
            config.roughness
        ),
        seed
    )
    fun generate() : WorldChunkData
    {
        val heightMapData = heightMap.generate()
        return WorldChunkData(x, y, heightMapData)
    }
}