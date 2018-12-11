package xyz.nietongxue.mindkit.model

data class FileFavorite(val path:String)

object Favorites {
    fun all():List<FileFavorite> =listOf(FileFavorite("./ppt.xmind"))
}