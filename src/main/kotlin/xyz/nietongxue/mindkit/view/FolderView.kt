package xyz.nietongxue.mindkit.view

import javafx.stage.DirectoryChooser
import tornadofx.Component
import java.io.File


class FolderView : Component() {
    //var folderSelected:((File)->Unit)?=null

    fun openChooser() : File?{
        val chooser = DirectoryChooser()
        chooser.title = "选择一个目录"

        val defaultDirectory = (config["selectedDirectory"] as? String ?: System.getProperty("user.home") ?: "/").let{File(it)}
        chooser.initialDirectory = defaultDirectory
        val selectedDirectory = chooser.showDialog(primaryStage)
        selectedDirectory?.also {
            config["selectedDirectory"] = it.absolutePath
            config.save()
        }

        return selectedDirectory
    }

}