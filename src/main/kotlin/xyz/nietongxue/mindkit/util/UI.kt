package xyz.nietongxue.mindkit.util

import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import tornadofx.insets


object Clipboard {

    fun setHTML(html:String){
        val clipboard = Clipboard.getSystemClipboard()
        val content = ClipboardContent()
        content.putHtml(html)
        clipboard.setContent(content)
    }
    fun setText(text:String){
        val clipboard = Clipboard.getSystemClipboard()
        val content = ClipboardContent()
        content.putString(text)
        clipboard.setContent(content)
    }
}

fun VBox.defaultPadding() {
    padding = insets(10)
    spacing = 10.0
}
fun HBox.defaultPadding(){
    padding = insets(10)
    spacing = 10.0
}
object Global {
    //TODO 这个实现丑陋吧？
    var resultPane: Pane? = null
}