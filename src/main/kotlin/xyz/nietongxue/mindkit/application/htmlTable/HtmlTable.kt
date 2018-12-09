package xyz.nietongxue.mindkit.application.htmlTable

import javafx.beans.property.SimpleStringProperty
import javafx.scene.Parent
import javafx.scene.layout.VBox
import tornadofx.*
import xyz.nietongxue.mindkit.application.AppDescriptor
import xyz.nietongxue.mindkit.application.Controller
import xyz.nietongxue.mindkit.model.Function
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.util.Clipboard
import xyz.nietongxue.mindkit.util.toHtml

object HtmlTable : AppDescriptor {
    // xmind  里面的table，生成html（或者markdown？）table，比如可以copy到conf
    // - 生成html可以（至少可以以safari打开，然后copy到conf editor）
    // 后续看看是否可以展示在fx里，然后copy到clipboard。
    override val name: String = "Html Table"
    override val description: String = "生成HTML Table，用于Copy到Conf"
    override val providedFunctions: List<Function> = listOf(object : Function {
        override fun process(node: Node): String {
            val table = Table.fromNode(node)
            return table.toHTML()
        }

        override val brief: String = "Html Table"
        override val description: String = "生成HTML Table，用于Copy到Conf"
    },
            object : Function {
                override fun process(node: Node): String {
                    return node.toHtml()
                }
                override val brief: String = "Html"
                override val description: String = "生成HTML"
            }
    )
    override val controller: Controller = TableController()
}

class TableController : Controller {
    val resultTextP = SimpleStringProperty()
    var resultText by resultTextP
    override fun process(node: Node) {
        resultText = (this.function?.process(node))
    }

    override var function: Function? = null

    override val view: View = object : View() {
        override val root: Parent = VBox()

        init {
            with(root) {
                splitpane {
                    scrollpane {
                        text(resultTextP)
                    }
                    vbox {
                        scrollpane {
                            webview {
                                dynamicContent(resultTextP) {
                                    engine.loadContent(it)
                                }
                            }
                        }
                        button("copy") {
                            action {
                                Clipboard.setHTML(resultText)
                            }
                        }
                    }
                }
            }
        }

    }
}

