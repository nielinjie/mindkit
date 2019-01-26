package xyz.nietongxue.mindkit.actions

import javafx.scene.Parent
import javafx.scene.layout.VBox
import tornadofx.label
import xyz.nietongxue.mindkit.application.xmind.XMindFavorite
import xyz.nietongxue.mindkit.application.xmind.XMindSource
import xyz.nietongxue.mindkit.model.Favorites
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.source.FileNode
import xyz.nietongxue.mindkit.source.Openable
import xyz.nietongxue.mindkit.util.Priority
import xyz.nietongxue.mindkit.util.defaultPadding
import java.awt.Desktop

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
        val oa: Action? =
                (node.source as? Openable)?.let {
                    (object : Action {
                        override fun view(node: Node): Parent? = VBox().apply {
                            defaultPadding()
                            label(node.title + " - 节点的来源文件已打开。")
                        }

                        override val brief: String = "打开"
                        override val description: String = "打开节点的来源文件 - "+ it.file.name
                        override fun action(node: Node) {
                            Desktop.getDesktop().open(it.file)
                        }
                    })
                }
        val ofa: Action? =
                (node as? FileNode)?.let {
                    (object : Action {
                        override fun view(node: Node): Parent? = VBox().apply {
                            defaultPadding()
                            label(node.title + " - 节点文件已打开。")
                        }

                        override val brief: String = "打开"
                        override val description: String = "打开节点文件 - " + it.file.name
                        override fun action(node: Node) {
                            Desktop.getDesktop().open(it.file)
                        }
                    })
                }

        return listOfNotNull(fa, fxa,oa,ofa)
    }

}