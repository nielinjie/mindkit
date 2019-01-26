package xyz.nietongxue.mindkit.application.marpPPT


import javafx.scene.layout.VBox
import tornadofx.*
import xyz.nietongxue.mindkit.util.Clipboard
import xyz.nietongxue.mindkit.util.defaultPadding
import xyz.nietongxue.mindkit.util.growV

class ResultView : View() {
    override val root = VBox()
    val controller: ProcessController by inject()

    init {
        with(root) {
            growV()
            defaultPadding()
            scrollpane {

                growV()
                text(controller.resultStringProperty)
            }
            hyperlink("拷贝") {
                action {
                    Clipboard.setText(controller.resultString)
                }
            }

        }
    }
}