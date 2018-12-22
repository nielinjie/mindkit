package xyz.nietongxue.mindkit.application.marpPPT

import javafx.scene.control.SplitPane
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import tornadofx.*
import xyz.nietongxue.mindkit.application.Function
import xyz.nietongxue.mindkit.application.xmind.Node
import xyz.nietongxue.mindkit.util.defaultPadding

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


    override val root = VBox()
    val controller: ProcessController by inject()
    val resultView: ResultView by inject()

    init {
        with(root) {
            defaultPadding()
            this += resultView
        }
    }
}