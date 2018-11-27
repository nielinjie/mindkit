package xyz.nietongxue.mindkit.view

import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import javafx.scene.layout.VBox
import tornadofx.*

class ResultView: View(){
    override val root= VBox()
    val controller: AppController by inject()
    init{
        with (root){
            this+=vbox {
                this += button("copy to clipboard"){
                    action{
                        val clipboard = Clipboard.getSystemClipboard()
                        val content = ClipboardContent()
                        content.putString(controller.resultString)
                        clipboard.setContent(content)
                    }
                }
                this += scrollpane {
                    text(controller.resultStringProperty)
                }

            }
        }
    }
}