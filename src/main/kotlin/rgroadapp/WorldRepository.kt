package rgroadapp

import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import rgroad.WorldConfig
import rgroad.WorldGenerator

interface WorldRepository {
    fun getWorld(config: WorldConfig, seed: String): WorldGenerator
}

@Component
class CachedWorldRepository: WorldRepository {
    @Cacheable("world")
    override fun getWorld(config: WorldConfig, seed: String): WorldGenerator {
        // TODO: handle seed (e.g. remove prefix, convert to long if possible)
        return WorldGenerator(config, seed)
    }

}