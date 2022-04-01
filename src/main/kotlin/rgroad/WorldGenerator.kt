package rgroad

import java.awt.image.BufferedImage


class WorldGenerator {
    val config: WorldConfig
    private val safeSeed: Long
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

    fun render(options: WorldRenderOptions? = defaultWorldRenderOptions, bound: List<Pair<Int, Int>>? = null): BufferedImage {
        val target = bound ?: chunks.keys
        if (!target.all { key -> key in chunks.keys }) {
            throw Exception("Invalid render target")
        }

        val minX = target.minByOrNull { it.first }!!.first
        val minY = target.minByOrNull { it.second }!!.second

        val canvas = BufferedImage(
            (target.maxByOrNull { it.first }!!.first - minX + 1) * config.chunkSize.toInt(),
            (target.maxByOrNull { it.second }!!.second - minY + 1) * config.chunkSize.toInt(),
            BufferedImage.TYPE_BYTE_GRAY
        )
        // TODO: implement render options
        target.forEach { key ->
            val heightMap = chunks[key]!!.heightMap
            canvas.graphics.drawImage(
                heightMap,
                (key.first - minX) * config.chunkSize.toInt(),
                (key.second - minY) * config.chunkSize.toInt(),
                null
            )
        }

        return canvas
    }
}

