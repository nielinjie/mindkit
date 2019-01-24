package xyz.nietongxue.mindkit.application.marpPPT


import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import tornadofx.*
import xyz.nietongxue.mindkit.util.Clipboard
import xyz.nietongxue.mindkit.util.growV

class ResultView: View(){
    override val root= VBox()
    val controller: ProcessController by inject()
    init{
        with (root){
            growV()
                button("copy to clipboard"){
                    action{
                        Clipboard.setText(controller.resultString)
                    }
                }
                scrollpane {
                    isFitToHeight = true
                    isFitToWidth = true
                   growV()
                    text(controller.resultStringProperty)
                }


        }
    }
}