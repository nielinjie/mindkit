package xyz.nietongxue.mindkit.view

import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import tornadofx.View
import tornadofx.label
import tornadofx.singleAssign
import tornadofx.vbox
import xyz.nietongxue.mindkit.util.defaultPadding

class ActionView: View(){
    override val root = VBox()
    var resultPanel : Pane by singleAssign()
    init {
        with(root) {
            defaultPadding()
            label("运行结果")
            resultPanel = vbox()
        }
    }
}