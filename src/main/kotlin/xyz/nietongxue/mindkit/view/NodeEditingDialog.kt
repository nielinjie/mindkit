package xyz.nietongxue.mindkit.view

import javafx.scene.control.TextInputDialog
import xyz.nietongxue.mindkit.util.orNull


fun nodeDialog(initString:String): String? {
    val dialog = TextInputDialog(initString)
    dialog.title = "Text Input Dialog"
    dialog.headerText = "Look, a Text Input Dialog"
    dialog.contentText = "Please enter your name:"
    return dialog.showAndWait().orNull()
}