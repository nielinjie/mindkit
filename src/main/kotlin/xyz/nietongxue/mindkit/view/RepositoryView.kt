package xyz.nietongxue.mindkit.view

import javafx.beans.property.SimpleObjectProperty
import javafx.scene.layout.VBox
import tornadofx.*
import xyz.nietongxue.mindkit.model.repository.Repository
import xyz.nietongxue.mindkit.util.defaultPadding
import xyz.nietongxue.mindkit.util.withAnother

class RepositoryView : View() {
    override val root = VBox()

    val repositoryP = SimpleObjectProperty<Repository>()
    val onRepositorySelectedP = SimpleObjectProperty<((Repository) -> Unit)?>(null)


    init {
        repositoryP.withAnother(onRepositorySelectedP).let {
            it.onChange { pair ->
                pair!!.second?.also { it(pair.first) }
            }
        }

        with(root) {
            hbox {
                defaultPadding()
                label(repositoryP.asString())
            }
        }


    }


    fun onClose() {

    }
}