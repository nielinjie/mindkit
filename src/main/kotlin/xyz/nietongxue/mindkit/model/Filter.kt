package xyz.nietongxue.mindkit.model

import xyz.nietongxue.mindkit.util.Priority
import xyz.nietongxue.mindkit.util.scanForInstance

typealias Filter = (Node) -> Boolean

data class Token(val name:String,val symbol:String?){
    companion object {
        val symbolStart = ":"
        fun fromString(string:String):Token{
            return if(string.startsWith(symbolStart)){
                Token(string.drop(1), symbolStart)
            }else Token(string,null)
        }
    }
    val withSymbol = ! (symbol.isNullOrBlank())
}

class Tokens(string:String){
    val all = string.split(" ").map {
        Token.fromString(it)
    }
    val withSymbol = all.filter { it.withSymbol }
    val withoutSymbol =  all.filterNot { it.withSymbol }

}


object Filters {
    fun filter(string: String): Filter {
        val tokens = Tokens(string)
        val filters = scanForInstance(FilterDescriptor::class).flatMap { it.filter(tokens) }
        return { node: Node ->
            filters.any {
                it(node)
            }
        }
    }
}

interface FilterDescriptor {
    fun filter(tokens: Tokens): List<Filter>
}

@Priority(1)
class TitleFilter : FilterDescriptor {
    override fun filter(tokens: Tokens): List<Filter> {
        return tokens.withoutSymbol.map {
            { node: Node ->
                node.title.contains(it.name)
            }
        }
    }

}