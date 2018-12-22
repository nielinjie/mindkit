package xyz.nietongxue.mindkit.application.marpPPT

import javafx.scene.layout.VBox
import tornadofx.View
import tornadofx.plusAssign
import xyz.nietongxue.mindkit.application.xmind.Node
import xyz.nietongxue.mindkit.util.defaultPadding

class ProcessView : View() {
    val view: View
        get() = this

    fun process(node: Node) {
        controller.process(node)
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