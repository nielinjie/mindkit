package xyz.nietongxue.mindkit.view

import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import javafx.scene.layout.VBox
import tornadofx.*

class ResultView: View(){
    override val root= VBox()
    val controller: ProcessorController by inject()
    init{
        with (root){
            vbox {
                button("copy to clipboard"){
                    action{
                        val clipboard = Clipboard.getSystemClipboard()
                        val content = ClipboardContent()
                        content.putString(controller.resultString)
                        clipboard.setContent(content)
                    }
                }
                scrollpane {
                    text(controller.resultStringProperty)
                }

            }
        }
    }
}