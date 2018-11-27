package xyz.nietongxue.mindkit.view

import javafx.scene.layout.VBox
import tornadofx.*

class ProcessorView : View(){
    override val root= VBox()
    val controller: AppController by inject()
    init{
        with(root){
            this+=vbox{
                this += scrollpane {
                    text(controller.processorStringProperty)
                }

            }
        }
    }
}