package xyz.nietongxue.mindkit.application.xmind

import xyz.nietongxue.mindkit.model.Favorite
import xyz.nietongxue.mindkit.source.Source

data class XMindFavorite(val path: String) : Favorite {
    override fun sources(): List<Source> {
        return listOf(XMindSource(path))
    }

    override val name = "XMind - $path"
}