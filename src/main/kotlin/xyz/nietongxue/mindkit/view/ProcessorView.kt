package xyz.nietongxue.mindkit.view

import javafx.scene.control.SplitPane
import tornadofx.*

class ProcessorView : View(){
    override val root= SplitPane()
    val controller: ProcessorController by inject()
    val resultView:ResultView by inject()
    init{
        with(root){
            vbox{
                scrollpane {
                    text(controller.processorP.stringBinding{
                        it?.description
                    })
                }

            }
            this+=resultView
        }
    }
}