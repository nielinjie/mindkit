package xyz.nietongxue.mindkit.view

import javafx.scene.control.SplitPane
import tornadofx.*
import jfxtras.styles.jmetro8.JMetro
import xyz.nietongxue.mindkit.application.marpPPT.ProcessorView


class MainView : View() {
    override val root = SplitPane()
    val sourceView: SourceView by inject()
    val processorsView: ProcessorsView by inject()

    init {
        JMetro(JMetro.Style.LIGHT).applyTheme(root)
        with(root) {
            this += sourceView.root
            //中间处理比如templates
                this += processorsView.root



        }



    }
}

class MyApp : App() {
    override val primaryView = MainView::class

}

fun main(args: Array<String>) {
    launch<MyApp>(args = args)
}
