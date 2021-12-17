package rgroad.world

import rgroad.getSafeSeed

class WorldGenerator (val config: WorldConfig, seed: String?)  {

    private val chunks: MutableMap<Pair<Int, Int>, WorldChunkData> = mutableMapOf<Pair<Int, Int>, WorldChunkData>()
    private val safeSeed : Long = getSafeSeed(seed)
    private val _seed = seed
    val seed: String
        get() = _seed ?: String.format("%d", safeSeed)


    constructor() : this(defaultWorldConfig, null)
    constructor(seed: Int) : this(defaultWorldConfig, String.format("%d", seed))
    constructor(config: WorldConfig) : this(config, null)
    constructor(config: WorldConfig, seed: Int) : this(config, String.format("%d", seed))


    fun getChunks(): MutableMap<Pair<Int, Int>, WorldChunkData> {
        return chunks
    }

    fun addChunk(x: Int, y: Int) {
        val coordinates = Pair(x, y)
        if (chunks.contains(coordinates)) {
            // TODO("use proper logging")
            println("Chunk already exists")
            return
        }
        val chunk = WorldChunk(coordinates, config, safeSeed)
        val chunkData = chunk.generate()
        chunks[coordinates] = chunkData
    }
}

