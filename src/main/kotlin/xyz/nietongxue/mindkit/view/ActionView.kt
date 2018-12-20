package xyz.nietongxue.mindkit.view

import javafx.scene.Parent
import javafx.scene.control.TabPane
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import tornadofx.View
import tornadofx.singleAssign
import tornadofx.tabpane
import tornadofx.vbox

class ActionView: View(){
    override val root: Parent = VBox()
    var resultPanel : Pane by singleAssign()
    init {
        with(root) {
            resultPanel = vbox()
        }
    }
}