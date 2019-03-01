package xyz.nietongxue.mindkit.view

import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Pos
import javafx.scene.layout.VBox
import tornadofx.*
import xyz.nietongxue.mindkit.model.Favorites
import xyz.nietongxue.mindkit.model.FolderFavorite
import xyz.nietongxue.mindkit.model.repository.FolderRepository
import xyz.nietongxue.mindkit.model.repository.Repository
import xyz.nietongxue.mindkit.util.defaultPadding
import xyz.nietongxue.mindkit.util.withAnother
import java.io.File

class RepositoryView : View() {
    override val root = VBox()
    private val folderView: FolderView = find()

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
                alignment = Pos.CENTER_LEFT
                label(repositoryP.stringBinding(){
                    it?.name() ?: "仓库"
                })
                hyperlink("打开") {
                    action {
                        val folder = folderView.openChooser()
                        folder?.let { openFolder(it) }
                    }
                }
            }
        }
    }

    private fun openFolder(folder: File) {
        val repository = FolderRepository(folder)
        this.repositoryP.set(repository)
    }

    fun onClose() {

    }
}