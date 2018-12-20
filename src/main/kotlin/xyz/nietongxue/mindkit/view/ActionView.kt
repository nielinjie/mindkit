package xyz.nietongxue.mindkit.view

import javafx.scene.Parent
import javafx.scene.control.TabPane
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import tornadofx.*

class ActionView: View(){
    override val root = VBox()
    var resultPanel : Pane by singleAssign()
    init {
        with(root) {
            label("运行结果")
            resultPanel = vbox()
        }
    }
}