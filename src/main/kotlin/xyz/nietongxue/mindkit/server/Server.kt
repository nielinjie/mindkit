package xyz.nietongxue.mindkit.server

import xyz.nietongxue.mindkit.model.repository.FolderRepository
import java.io.File
import java.util.concurrent.atomic.AtomicLong

@SpringBootApplication
class Application{


    @Bean
    val repository = FolderRepository(File("/Users/nielinjie/Desktop/testRepository/"))

}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}


@RestController
class GreetingController {

    val counter = AtomicLong()

    @GetMapping("/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String) =
            Greeting(counter.incrementAndGet(), "Hello, $name")

}


data class Greeting(val id: Long, val content: String)