package xyz.nietongxue.mindkit.application.htmlTable

import javafx.beans.property.SimpleStringProperty
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import tornadofx.*
import xyz.nietongxue.mindkit.application.AppDescriptor
import xyz.nietongxue.mindkit.application.AppController
import xyz.nietongxue.mindkit.model.Function
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.model.XNode
import xyz.nietongxue.mindkit.util.Clipboard
import xyz.nietongxue.mindkit.util.toHtml
import java.lang.IllegalStateException

object HtmlTable : AppDescriptor {
    // xmind  里面的table，生成html（或者markdown？）table，比如可以copy到conf
    // - 生成html可以（至少可以以safari打开，然后copy到conf editor）
    // 后续看看是否可以展示在fx里，然后copy到clipboard。
    override val name: String = "Html Table"
    override val description: String = "生成HTML Table，用于Copy到Conf"
    override val providedFunctions: List<Function> = listOf(object : Function {
        override fun process(node: Node): String {
            if(node is XNode) {
                val table = Table.fromNode(node)
                return table.toHTML()
            }else{
                throw IllegalStateException("html table 只支持xmind")
            }
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
    override val appController: AppController = TableAppController()
}

class TableAppController : AppController {
    val resultTextP = SimpleStringProperty()
    var resultText: String by resultTextP
    override fun process(node: Node) {
        runAsync {
            this@TableAppController.function?.process(node)
        } ui {
            resultText = it ?: ""
        }
    }

    override var function: Function? = null

    override val view: View = object : View() {
        override val root: VBox = VBox()

        init {
            with(root) {
                vboxConstraints {
                    this.vGrow = Priority.ALWAYS
                }
                splitpane {
                    vboxConstraints {
                        this.vGrow = Priority.ALWAYS
                    }

                    scrollpane {
                        isFitToHeight = true
                        isFitToWidth = true
                        vboxConstraints {
                            this.vGrow = Priority.ALWAYS
                        }
                        text(resultTextP)
                    }
                    vbox {
                        scrollpane {
                            isFitToHeight = true
                            isFitToWidth = true
                            vboxConstraints {
                                this.vGrow = Priority.ALWAYS
                            }
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

