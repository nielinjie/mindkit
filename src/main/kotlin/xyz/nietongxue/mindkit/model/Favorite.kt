package xyz.nietongxue.mindkit.model


interface Favorite {
    fun updateNode(root:Node):List<Node>
    val name:String
}

data class XMindFavorite(val path:String) : Favorite{
    override fun updateNode(root:Node):List<Node>{
        return XMindSource(path).appendTo(root).what
    }
    override val name = "XMind - $path"
}

object Favorites {
    val all:List<XMindFavorite> =listOf(
            XMindFavorite("./ppt.xmind"),
            XMindFavorite("/Users/nielinjie/Desktop/19年计划.xmind")
    )
}