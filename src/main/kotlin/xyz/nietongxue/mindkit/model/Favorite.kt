package xyz.nietongxue.mindkit.model

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import tornadofx.Component
import xyz.nietongxue.mindkit.model.source.FileSourceDescriptor
import xyz.nietongxue.mindkit.model.source.FolderSource
import xyz.nietongxue.mindkit.model.source.Source
import xyz.nietongxue.mindkit.util.scanForInstance
import java.io.File


interface Favorite {
    //NOTE favorite是对tree的一个快照，包括有哪些source和某些需要重现的状态，比如选中某个特定的node。
    //NOTE 相对于source，favorite是个比较上层和局部的概念，不涉及核心逻辑。
    fun sources(): List<Source>

    fun name(): String
}

data class FolderFavorite(val path: String) : Favorite {
    override fun sources(): List<Source> {
        return listOf(FolderSource(path))
    }

    override fun name() = "Folder - $path"
}
data class FileFavorite(val path: String) : Favorite {
    private val fileSourceDescriptors = scanForInstance(FileSourceDescriptor::class)
    override fun sources(): List<Source> {
        return fileSourceDescriptors.flatMap { it.fileToSource(File(path)) }
    }

    override fun name() = "File - $path"
}

object Favorites : Component() {
    val all: MutableList<Favorite> = mutableListOf()
    val default: List<Favorite> = listOf(
            FileFavorite("/Users/nielinjie/Desktop/ppt.xmind")
            ,
            FileFavorite("/Users/nielinjie/Desktop/19年计划.xmind"),
            FolderFavorite("/Users/nielinjie/Desktop"),
            FolderFavorite("/Users/nielinjie/Library/Mobile Documents/com~apple~CloudDocs/思考和写作")
    )

    init {
        val jsonS: String = (config["favorites"] as? String) ?: "[]"
        val favorites: List<Favorite> = (Parser.default().parse(jsonS.reader()) as JsonArray<JsonObject>).mapNotNull {
            val t = it.string("_type")!!
            val ob = it.obj("favorite")!!
            when (t) {
                //TODO 搞一个跟一般的模式，不要when，比如forName
                "xyz.nietongxue.mindkit.model.FolderFavorite" -> Klaxon().parseFromJsonObject<FolderFavorite>(ob)
                "xyz.nietongxue.mindkit.model.FileFavorite" -> Klaxon().parseFromJsonObject<FileFavorite>(ob)
                else -> throw IllegalArgumentException("Unknown type: $t")
            }
        }
        all.addAll(if (favorites.isEmpty()) default else favorites)
    }

    fun add(favorite: Favorite) {
        if (!all.contains(favorite))
            all.add(favorite)
    }

    fun save() {
        data class FavoriteData(
                val _type: String,
                val favorite: Favorite)

        val list: List<FavoriteData> = all.map { FavoriteData(it.javaClass.name, it) }
        val jsonS = Klaxon().toJsonString(list)
        config["favorites"] = jsonS
        config.save()
    }
}



