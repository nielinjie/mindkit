package xyz.nietongxue.mindkit.view

import javafx.scene.Parent
import javafx.scene.control.TabPane
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import tornadofx.*
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