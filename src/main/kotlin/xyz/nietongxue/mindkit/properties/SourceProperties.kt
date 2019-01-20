package xyz.nietongxue.mindkit.properties

import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Pos
import tornadofx.*
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.source.Openable
import xyz.nietongxue.mindkit.util.Priority
import xyz.nietongxue.mindkit.util.defaultPadding
import java.awt.Desktop
import java.io.File

@Priority(-5)
object SourceProperties : Properties {
    override fun fieldSet(nodeP: SimpleObjectProperty<Node>): List<Fieldset> {
        val re =
                Fieldset("来源信息")
        with(re) {
            field {
                //TODO wrap 没有起作用
                hbox {
                    alignment = Pos.CENTER
                    label(nodeP.value ?.source?.description ?: "（不明）" ) {
                        isWrapText = true
                    }
                    (nodeP.value.source as? Openable)?.let {
                        hyperlink("打开") {
                            minWidth = 40.0
                            action {
                                Desktop.getDesktop().open(it.file)
                            }
                        }
                    }
                }

            }
        }
        return listOf(re)

    }
}