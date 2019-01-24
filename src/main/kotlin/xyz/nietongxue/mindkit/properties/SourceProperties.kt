package xyz.nietongxue.mindkit.properties

import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Pos
import tornadofx.*
import xyz.nietongxue.mindkit.application.xmind.XMindFavorite
import xyz.nietongxue.mindkit.application.xmind.XMindSource
import xyz.nietongxue.mindkit.model.Favorites
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

                    label(nodeP.value ?.source?.description ?: "（不明）" ) {
                        isWrapText = true
                    }
            }
        }
        return listOf(re)

    }
}