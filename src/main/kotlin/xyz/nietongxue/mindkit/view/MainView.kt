package xyz.nietongxue.mindkit.view

import javafx.scene.control.SplitPane
import tornadofx.App
import tornadofx.View
import tornadofx.launch
import tornadofx.plusAssign
import xyz.nietongxue.mindkit.util.Global


class MainView : View() {
    override val root = SplitPane()
    val sourceView: SourceView by inject()
    val functionsView: FunctionsView by inject()
    val actionView: ActionView by inject()

    init {
//        JMetro(JMetro.Style.LIGHT).applyTheme(root)
        Global.tabPane = actionView.tabP
        with(root) {
            this += sourceView.root
            //中间处理比如templates
            this += functionsView.root
            this += actionView.root
        }

    }

    override fun onUndock() {
        config["width"] = this.currentWindow?.width?.toString()
        config["height"] = this.currentWindow?.height?.toString()

        config["left"] = this.currentWindow?.x?.toString()
        config["top"] = this.currentWindow?.y?.toString()
        config.save()
        super.onUndock()
    }

    override fun onDock() {
        config.double("width")?.also{this.currentWindow?.width = it }
        config.double("height")?.also{this.currentWindow?.height = it }

        config.double("left")?.also { this.currentWindow?.x = it }
        config.double("top")?.also { this.currentWindow?.y = it }

        super.onDock()
    }
}

class MyApp : App() {
    override val primaryView = MainView::class

}

fun main(args: Array<String>) {
    launch<MyApp>(args = args)
}
