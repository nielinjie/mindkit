package xyz.nietongxue.mindkit.application.marpPPT

import javafx.scene.control.SplitPane
import javafx.scene.layout.Priority
import tornadofx.*
import xyz.nietongxue.mindkit.application.Function
import xyz.nietongxue.mindkit.application.xmind.Node

class ProcessView : View() {
    val view: View
        get() = this

    fun process(node: Node) {
        controller.process(node)
    }

    var function: Function?
        get() = controller.function
        set(value) {
            controller.function = value
        }


    override val root = SplitPane()
    val controller: ProcessController by inject()
    val resultView: ResultView by inject()

    init {
        with(root) {
            vboxConstraints {
                this.vGrow = Priority.ALWAYS
            }
            vbox {
                vboxConstraints {
                    this.vGrow = Priority.ALWAYS
                }
                scrollpane {
                    isFitToHeight = true
                    isFitToWidth = true
                    vboxConstraints {
                        this.vGrow = Priority.ALWAYS
                    }
                    text(controller.processorP.stringBinding {
                        it?.description
                    })
                }

            }
            this += resultView
        }
    }
}