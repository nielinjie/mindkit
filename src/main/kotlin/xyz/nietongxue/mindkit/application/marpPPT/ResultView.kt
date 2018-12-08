package xyz.nietongxue.mindkit.application.marpPPT


import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import tornadofx.*
import xyz.nietongxue.mindkit.util.Clipboard

class ResultView: View(){
    override val root= VBox()
    val controller: ProcessController by inject()
    init{
        with (root){
            vboxConstraints {
                this.vGrow = Priority.ALWAYS
            }
                button("copy to clipboard"){
                    action{
                        Clipboard.setText(controller.resultString)
                    }
                }
                scrollpane {
                    isFitToHeight = true
                    isFitToWidth = true
                    vboxConstraints {
                        this.vGrow = Priority.ALWAYS
                    }
                    text(controller.resultStringProperty)
                }


        }
    }
}