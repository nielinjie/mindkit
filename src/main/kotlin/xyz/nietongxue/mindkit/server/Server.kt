package xyz.nietongxue.mindkit.server

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import xyz.nietongxue.mindkit.model.Marker
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.model.repository.FolderRepository
import xyz.nietongxue.mindkit.model.source.InternalSource
import xyz.nietongxue.mindkit.model.source.Source
import java.io.File
import java.util.concurrent.atomic.AtomicLong

@SpringBootApplication
class Application {

    @Bean
    fun graph(): Graph {
        val repository = FolderRepository(File("/Users/nielinjie/Desktop/testRepository/"))
        val root = object : Node {
            override val markers: MutableList<Marker> = mutableListOf()
            override val id: String = "_root"
            override var title: String = "/"
            override val children: ArrayList<Node> = ArrayList()
            override val source: Source = InternalSource
        }
        repository.sources().flatMap {
            it.mount(root)
        }.forEach {
            root.findById(it.where)?.children?.addAll(it.getAndMark())
        }
        return Graph.fromNodes(root)
    }

//    @Bean
//    val repository = FolderRepository(File("/Users/nielinjie/Desktop/testRepository/"))

}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}


@RestController
class GreetingController {

    val counter = AtomicLong()

    @Autowired
    var g: Graph? = null

    @GetMapping("/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String) =
            Greeting(counter.incrementAndGet(), "Hello, $name")

    @GetMapping("/graph")
    fun graph() = g!!
}


data class Greeting(val id: Long, val content: String)