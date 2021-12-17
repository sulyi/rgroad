package rgroad.world

import java.awt.Image

data class WorldChunkData(val offset_x:Int, val offset_y:Int, val heightMap: Image)
data class WorldConfig(val chunkSize:UInt, val height:Float, val roughness:Float)

val defaultWorldConfig = WorldConfig(256u, 1f, .5f)