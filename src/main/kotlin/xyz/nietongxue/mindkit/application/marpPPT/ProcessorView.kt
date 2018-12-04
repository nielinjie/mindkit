package xyz.nietongxue.mindkit.application.marpPPT

import javafx.scene.control.SplitPane
import javafx.scene.layout.Priority
import tornadofx.*
import xyz.nietongxue.mindkit.application.Controller
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.model.Processor

class ProcessorView : View(), Controller {
    override val view: View
        get() = this

    override fun process(node: Node) {
        controller.process(node)
    }

    override var processor: Processor?
        get() = controller.processor
        set(value) {
            controller.processor = value
        }


    override val root = SplitPane()
    val controller: ProcessorController by inject()
    val resultView: ResultView by inject()

    init {
        with(root) {
            vbox {
                scrollpane {
                    isFitToHeight = true;
                    isFitToWidth = true;
                    vboxConstraints {
                        this.vGrow = Priority.ALWAYS
                    }
                    text(controller.processorP.stringBinding {
                        it?.description
                    })
                }

            }
            this += resultView
        }
    }
}