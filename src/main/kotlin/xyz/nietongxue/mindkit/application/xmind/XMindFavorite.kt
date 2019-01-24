package xyz.nietongxue.mindkit.application.xmind

import xyz.nietongxue.mindkit.model.Favorite
import xyz.nietongxue.mindkit.source.Source


/*TODO  Favorite提到外层，直接save source。
      或者应该save file？
*/
data class XMindFavorite(val path: String) : Favorite {
    override fun sources(): List<Source> {
        return listOf(XMindSource(path))
    }

    override fun name() = "XMind - $path"
}