package xyz.nietongxue.mindkit.view

import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import tornadofx.View
import tornadofx.singleAssign
import tornadofx.vbox
import xyz.nietongxue.mindkit.util.defaultPadding
import xyz.nietongxue.mindkit.util.growV

class ActionView: View(){
    override val root = VBox()
    var resultPanel : Pane by singleAssign()
    init {
        with(root) {
            defaultPadding()
            resultPanel = vbox{
                growV()
            }
        }
    }
}