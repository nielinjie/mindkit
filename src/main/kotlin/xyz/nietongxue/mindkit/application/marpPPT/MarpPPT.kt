package xyz.nietongxue.mindkit.application.marpPPT

import tornadofx.find
import xyz.nietongxue.mindkit.application.Function
import xyz.nietongxue.mindkit.application.TemplateFunction

object MarpPPT {
     val providedFunctions: List<Function> by lazy{
        listOf(
                object : TemplateFunction(MarpPPT::class.java.getResource("/marpSlide.twig").readText()) {

                    override val brief = "Marp Slides 模板"
                    override val description = this.templateString

                })
    }
    val appController = find(ProcessView::class)

}

