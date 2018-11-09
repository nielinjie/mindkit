package xyz.nietongxue.mindkit.model

data class Node(val id:String, val title:String,val children:List<Node>){
    companion object {
        fun fromJson(json:dynamic):Node{
            val children:Array<*> =json.children?.attached ?: arrayOf<Any>() as Array<*>
            return Node(json.id,json.title,children.map{
                Node.fromJson(it)
            }.toList())
        }
    }
    fun pretty(){
        print("====")
        print(title)
        print("====")
        println()
        children.forEach {
            it.pretty()
        }
    }
}

data class Sheet(val root:Node){
    companion object {
        fun fromJson(json:dynamic):Sheet {
            return Sheet(Node.fromJson(json.rootTopic))
        }
    }
}

data class MindMap(val sheets:List<Sheet>) {
    companion   object  {
        fun fromJson(json:dynamic):MindMap{
            return MindMap(listOf(Sheet.fromJson(json[0])))
        }
    }
    fun pretty(){
        this.sheets[0].root.pretty()
    }
}