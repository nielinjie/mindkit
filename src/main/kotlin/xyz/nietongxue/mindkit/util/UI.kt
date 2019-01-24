package xyz.nietongxue.mindkit.util

import javafx.scene.Node
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import tornadofx.*


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
fun Node.growV(){
    vboxConstraints {
        vgrow = Priority.ALWAYS
    }
}

fun HBox.defaultPadding(){
    padding = insets(10)
    spacing = 10.0
}
fun Node.growH(){
    hboxConstraints {
        hgrow = Priority.ALWAYS
    }
}


fun KeyEvent?.metaRight(): Boolean {
     return this?.isMetaDown?.and( this.code == KeyCode.RIGHT) == true
}

fun KeyEvent?.metaF(): Boolean {
    return this?.isMetaDown?.and( this.code == KeyCode.F) == true
}

object UIGlobal {
    //TODO 这个实现丑陋吧？
    var resultPane: Pane? = null
}