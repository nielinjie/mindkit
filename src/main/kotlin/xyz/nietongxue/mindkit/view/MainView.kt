package xyz.nietongxue.mindkit.view

import javafx.scene.control.SplitPane
import jfxtras.styles.jmetro8.JMetro
import tornadofx.App
import tornadofx.View
import tornadofx.launch
import tornadofx.plusAssign


class MainView : View() {
    //TODO 主window的prefer size，resize
    //TODO 各个view的layout，比如margin、resize时control的位置策略
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

    override fun onUndock() {
        config.set("width",this.currentWindow?.width?.toString())
        config.set("height",this.currentWindow?.height?.toString())
        config.save()
        super.onUndock()
    }

    override fun onDock() {
        config.double("width")?.also{this.currentWindow?.width = it }
        config.double("height")?.also{this.currentWindow?.height = it }


        super.onDock()
    }
}

class MyApp : App() {
    override val primaryView = MainView::class

}

fun main(args: Array<String>) {
    launch<MyApp>(args = args)
}
