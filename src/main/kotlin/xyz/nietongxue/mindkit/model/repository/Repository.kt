package xyz.nietongxue.mindkit.model.repository

import xyz.nietongxue.mindkit.model.source.EditableSource
import xyz.nietongxue.mindkit.model.source.FolderSource
import xyz.nietongxue.mindkit.model.source.Source
import java.io.File

interface Repository {
    fun sources(): List<Source>

    fun name(): String
    fun nodeAddSource():EditableSource?=null
}

data class FolderRepository(val base: File) : Repository {
    /*
        /--（root）（repository的主要形式）包括其下所有的文件，可以外链（文件、folder、其他一切）。
            --- .mindkit 文件夹
                --- nodes.json nodes
                --- (其他各种，元数据、外链等的json store)
            ---（其他文件可以存在，作为一般文件用文件source来load)
     */

    var nodesSource:MindKitFileSource?  = null
    init{
        checkAndCreate()
    }
    private fun checkAndCreate(){
        val mindkitFolder = File(base,".mindkit")
        if(mindkitFolder.isDirectory){
            nodesSource = MindKitFileSource(File(mindkitFolder,"nodes.json"))
        }else{
            if (mindkitFolder.isFile){
                throw IllegalStateException(".mindkit 文件存在，无法建立相关文件结构")
            }else{
               if( mindkitFolder.mkdir()) {
                   checkAndCreate()
                   return
               }

            }
            throw IllegalStateException("无法建立相关文件结构")
        }
    }
    override fun sources(): List<Source> {
        return listOfNotNull(
                FolderSource(base.absolutePath),
                this.nodesSource
                //outerLinkSources
        )
    }

    override fun name(): String {
        return "仓库 - ${base.name}"
    }

    override fun nodeAddSource(): EditableSource? {
        return this.nodesSource
    }
}
