package xyz.nietongxue.mindkit.actions

import javafx.scene.Parent
import javafx.scene.layout.VBox
import tornadofx.label
import xyz.nietongxue.mindkit.application.xmind.XMindFavorite
import xyz.nietongxue.mindkit.application.xmind.XMindSource
import xyz.nietongxue.mindkit.model.Favorites
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.source.FileNode
import xyz.nietongxue.mindkit.util.Priority
import xyz.nietongxue.mindkit.util.defaultPadding

@Priority(-1)
object GeneralActions : ActionDescriptor {
    override fun actions(node: Node): List<Action> {
        val fa: Action? =
        //TODO XMind 解耦，可能收藏FileSource。FileSource从具体的文件类型中抽象出来。
                (node.source as? XMindSource)?.let {
                    (object : Action {
                        override fun view(node: Node): Parent? = VBox().apply {
                            defaultPadding()
                            label(node.title + " - 节点的来源已被加入收藏。")
                        }

                        override val brief: String = "收藏"
                        override val description: String = "收藏节点的来源 - "+ it.file.name
                        override fun action(node: Node) {
                            Favorites.add(XMindFavorite(it.file.absolutePath))
                        }
                    })
                }
        val fxa: Action? =
        //TODO XMind 解耦，可能收藏FileSource。FileSource从具体的文件类型中抽象出来。
                (node as? FileNode)?.let {
                    if ( it.file.isFile && it.file.extension == "xmind") {
                        (object : Action {
                            override fun view(node: Node): Parent? = VBox().apply {
                                defaultPadding()
                                label(node.title + " - 节点已被加入收藏。")
                            }

                            override val brief: String = "收藏"
                            override val description: String = "收藏节点 - "+ it.file.name
                            override fun action(node: Node) {
                                Favorites.add(XMindFavorite(it.file.absolutePath))
                            }
                        })
                    }else null
                }


        return listOfNotNull(fa, fxa)
    }

}