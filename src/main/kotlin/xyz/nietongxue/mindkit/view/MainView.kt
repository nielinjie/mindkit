package xyz.nietongxue.mindkit.view

import javafx.scene.control.SplitPane
import tornadofx.*
import xyz.nietongxue.mindkit.actions.ActionDescriptor
import xyz.nietongxue.mindkit.properties.Properties
import xyz.nietongxue.mindkit.util.Global
import xyz.nietongxue.mindkit.util.scanForInstance


class MainView : View() {
    override val root = SplitPane()
    val sourceView: SourceView by inject()
    val appView: AppView by inject()
    val actionView: ActionView by inject()

    init {
//        JMetro(JMetro.Style.LIGHT).applyTheme(root)
        Global.resultPane = actionView.resultPanel
        with(root) {
            this += sourceView.root
            //中间处理比如templates
            this += appView.root
            this += actionView.root
        }

    }

    override fun onUndock() {


        sourceView.favoriteView.onClose()

        config["width"] = this.currentWindow?.width?.toString()
        config["height"] = this.currentWindow?.height?.toString()

        config["left"] = this.currentWindow?.x?.toString()
        config["top"] = this.currentWindow?.y?.toString()
        config.save()
        super.onUndock()
    }

    override fun onDock() {
        config.double("width")?.also { this.currentWindow?.width = it }
        config.double("height")?.also { this.currentWindow?.height = it }

        config.double("left")?.also { this.currentWindow?.x = it }
        config.double("top")?.also { this.currentWindow?.y = it }

        super.onDock()
    }
}

class MyApp : App() {
    override val primaryView = MainView::class

    init {
        runAsync {
            scanForInstance(Properties::class)
            scanForInstance(ActionDescriptor::class)
        }
    }
}

fun main(args: Array<String>) {
    launch<MyApp>(args = args)
}
