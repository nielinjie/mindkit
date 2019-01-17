package xyz.nietongxue.mindkit.application.xmind

import javafx.beans.property.SimpleObjectProperty
import javafx.scene.image.Image
import tornadofx.*
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.properties.Properties

object XMindProperties : Properties {
    override fun fieldSet(nodeP: SimpleObjectProperty<Node>): List<Fieldset> {
        val node = nodeP.value
        return if (node is XNode) {
            listOf(Fieldset("XMind信息")
                    .apply {
                        field("标题") {
                            label(node.title)
                        }
                        //TODO markers在xmind以外来做。
                        if(node.markers.isNotEmpty()) {
                            field("Markers") {
                                label(node.markers.map { it.name }.joinToString(","))
                            }
                        }
                        node.image?.also {
                            field("图像") {
                                //TODO 是否有其他类型的image？
                                (node.source as? XMindSource)?.xMindFile?.resource(it.src)?.also {
                                    imageview(javafx.scene.image.Image(it, 100.0, 100.0, true, true)) {
                                    }
                                }
                            }
                        }
                    }
            )
        } else {
            emptyList()
        }
    }
}