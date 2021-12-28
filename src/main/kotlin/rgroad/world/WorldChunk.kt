package rgroad.world

import rgroad.*

class WorldChunk(private val x: Int, private val y: Int, config: WorldConfig, seed: Long) {
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