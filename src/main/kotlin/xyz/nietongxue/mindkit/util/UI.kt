package xyz.nietongxue.mindkit.util

import com.beust.klaxon.internal.firstNotNullResult
import javafx.scene.Node
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import tornadofx.*
import xyz.nietongxue.mindkit.view.ViewNode


object Clipboard {

    fun setHTML(html: String) {
        val clipboard = Clipboard.getSystemClipboard()
        val content = ClipboardContent()
        content.putHtml(html)
        clipboard.setContent(content)
    }

    fun setText(text: String) {
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

fun Node.growV() {
    vboxConstraints {
        vgrow = Priority.ALWAYS
    }
}

fun HBox.defaultPadding() {
    padding = insets(10)
    spacing = 10.0
}

fun Node.growH() {
    hboxConstraints {
        hgrow = Priority.ALWAYS
    }
}

fun KeyEvent?.metaAnd(name: String): Boolean {
    return this?.isMetaDown?.and(this.code == KeyCode.getKeyCode(name)) == true
}


object UIGlobal {
    //TODO 这个实现丑陋吧？
    var resultPane: Pane? = null
    var treeView: TreeView<ViewNode>? = null
}

fun <T> TreeView<T>.findItem(item: TreeItem<T>, f: (T) -> Boolean): TreeItem<T>? {
    return if (f(item.value)) {
        item
    } else {
        item.children.firstNotNullResult {
            this.findItem(it, f)
        }
    }
}

fun <T> TreeView<T>.ensureVisibleItem(item: TreeItem<T>, f: (T) -> Boolean) {
    this.findItem(item, f)?.let {
        expandToItem(it)
    }
}

fun <T> TreeView<T>.expandToItem(item: TreeItem<T>) {
    if (item.parent == null) return
    item.parent.isExpanded = true
    if (item.parent.parent != this.root) {
        expandToItem(item.parent)
    }
}