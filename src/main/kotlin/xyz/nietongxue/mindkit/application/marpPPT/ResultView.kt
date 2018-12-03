package xyz.nietongxue.mindkit.application.marpPPT

import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import tornadofx.*

class ResultView: View(){
    override val root= VBox()
    val controller: ProcessorController by inject()
    init{
        with (root){
            vboxConstraints {
                this.vGrow = Priority.ALWAYS
            }
                button("copy to clipboard"){
                    action{
                        val clipboard = Clipboard.getSystemClipboard()
                        val content = ClipboardContent()
                        content.putString(controller.resultString)
                        clipboard.setContent(content)
                    }
                }
                scrollpane {
                    isFitToHeight = true;
                    isFitToWidth = true;
                    vboxConstraints {
                        this.vGrow = Priority.ALWAYS
                    }
                    text(controller.resultStringProperty)
                }


        }
    }
}