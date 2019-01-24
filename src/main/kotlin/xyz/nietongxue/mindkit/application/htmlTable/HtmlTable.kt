package xyz.nietongxue.mindkit.application.htmlTable

import javafx.beans.property.SimpleStringProperty
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import tornadofx.*
import xyz.nietongxue.mindkit.util.Clipboard
import xyz.nietongxue.mindkit.util.defaultPadding
import xyz.nietongxue.mindkit.util.growV

object HtmlTable {
    // xmind  里面的table，生成html（或者markdown？）table，比如可以copy到conf
    // - 生成html可以（至少可以以safari打开，然后copy到conf editor）
    // 后续看看是否可以展示在fx里，然后copy到clipboard。


    val appController = TableAppController()
}

class TableAppController {
    val resultTextP = SimpleStringProperty()
    var resultText: String by resultTextP


    val view: View = object : View() {
        override val root: VBox = VBox()

        init {
            with(root) {

                defaultPadding()
                growV()
                scrollpane {
                    isFitToHeight = true
                    isFitToWidth = true
                    growV()
                    webview {
                        dynamicContent(resultTextP) {
                            engine.loadContent(it)
                        }
                    }
                }
                hyperlink("拷贝") {
                    action {
                        Clipboard.setHTML(resultText)
                    }
                }
            }

        }


    }
}

