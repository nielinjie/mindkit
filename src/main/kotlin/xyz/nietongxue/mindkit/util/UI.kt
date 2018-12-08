package xyz.nietongxue.mindkit.util

import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent

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