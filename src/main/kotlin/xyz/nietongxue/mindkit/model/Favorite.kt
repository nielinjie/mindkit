package xyz.nietongxue.mindkit.model


interface Favorite {
    //NOTE favorite是对tree的一个快照，包括有哪些source和某些需要重现的状态，比如选中某个特定的node。
    //NOTE 相对于source，favorite是个比较上层和局部的概念，不涉及核心逻辑。
    fun sources(): List<Source>

    val name: String
}

data class XMindFavorite(val path: String) : Favorite {
    override fun sources(): List<Source> {
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