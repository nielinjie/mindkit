package xyz.nietongxue.mindkit.view

import javafx.stage.DirectoryChooser
import javafx.stage.FileChooser
import tornadofx.Component
import java.io.File


class FolderView : Component() {

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

class FileView : Component() {

    fun openChooser() : File?{
        val chooser = FileChooser()
        chooser.title = "选择一个文件"

        val defaultDirectory = (config["selectedFileDirectory"] as? String ?: System.getProperty("user.home") ?: "/").let{File(it)}
        chooser.initialDirectory = defaultDirectory
        val selectedFile = chooser.showOpenDialog(primaryStage)
        selectedFile?.also {
            config["selectedFileDirectory"] = it.parentFile.absolutePath
            config.save()
        }

        return selectedFile
    }

}