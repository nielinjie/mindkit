package xyz.nietongxue.mindkit.model


interface Favorite {
    //TODO 考虑是否需要从非root的去load
    //TODO 也许所有的favorite都应该是返回source？
    fun load(): List<Source>

    val name: String
}

data class XMindFavorite(val path: String) : Favorite {
    override fun load(): List<Source> {
        return listOf(XMindSource(path))
    }

    override val name = "XMind - $path"
}

object Favorites {
    val all: List<XMindFavorite> = listOf(
            XMindFavorite("./ppt.xmind"),
            XMindFavorite("/Users/nielinjie/Desktop/19年计划.xmind")
    )
}