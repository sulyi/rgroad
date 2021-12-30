package rgroad

class WorldGenerator {
    val config: WorldConfig
    private val safeSeed : Long
    private val _chunks: MutableMap<Pair<Int, Int>, WorldChunkData> = mutableMapOf()
    private val _seed: String?

    constructor(config: WorldConfig, seed: String?) {
        this.config = config
        this.safeSeed = getSafeSeed(seed)
        this._seed = seed
    }
    constructor(config: WorldConfig, seed: Long) {
        this.config = config
        this.safeSeed = seed
        this._seed = null
    }
    constructor() : this(defaultWorldConfig, null)
    constructor(seed: Long) : this(defaultWorldConfig, seed)
    constructor(config: WorldConfig) : this(config, null)

    val seed: String
        get() = _seed ?: String.format("%d", safeSeed)

    val chunks: Map<Pair<Int, Int>, WorldChunkData>
        get() = _chunks.toMap()

    fun addChunk(x: Int, y: Int) {
        val coordinates = Pair(x, y)
        if (chunks.contains(coordinates)) {
            // TODO("use proper logging")
            println("Chunk already exists")
            return
        }
        val chunk = WorldChunk(x, y, config, safeSeed)
        val chunkData = chunk.generate()
        _chunks[coordinates] = chunkData
    }
}

