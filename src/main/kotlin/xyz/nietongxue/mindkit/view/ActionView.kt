package xyz.nietongxue.mindkit.view

import javafx.scene.Parent
import javafx.scene.control.TabPane
import javafx.scene.layout.VBox
import tornadofx.View
import tornadofx.singleAssign
import tornadofx.tabpane

class ActionView: View(){
    override val root: Parent = VBox()
    var tabP : TabPane by singleAssign()
    init{
        with(root){
            tabP = tabpane()
        }
    }

}