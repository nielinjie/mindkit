package xyz.nietongxue.mindkit.view

import javafx.scene.control.SplitPane
import javafx.scene.input.Clipboard
import tornadofx.*
import javafx.scene.input.ClipboardContent
import jfxtras.styles.jmetro8.JMetro


class MainView : View() {
    override val root = SplitPane()
    val controller :AppController by inject()
    val sourceView:SourceView by inject()
    val processorView :ProcessorView by inject()
    val resultView :ResultView by inject()

    init {
        JMetro(JMetro.Style.LIGHT).applyTheme(root)
        with(root) {
            this += sourceView.root
            //中间处理比如templates
            this += processorView.root

            //结果
            this += resultView.root

        }



    }
}

class MyApp : App() {
    override val primaryView = MainView::class

}

fun main(args: Array<String>) {
    launch<MyApp>(args = args)
}
