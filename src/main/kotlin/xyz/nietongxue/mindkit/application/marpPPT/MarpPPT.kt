package xyz.nietongxue.mindkit.application.marpPPT

import tornadofx.find
import xyz.nietongxue.mindkit.application.AppDescriptor
import xyz.nietongxue.mindkit.model.Function
import xyz.nietongxue.mindkit.model.TemplateFunction

object MarpPPT : AppDescriptor {
    override val providedFunctions: List<Function> by lazy{
        listOf(
        object : TemplateFunction(MarpPPT::class.java.getResource("/marpSlide.twig").readText()) {

            override val brief = "Marp Slides 模板"
            override val description = this.templateString

        })
    }

    override val name: String= "Marp PPT"
    override val description:String = "Marp PPT"

    override val controller = find(ProcessView::class)

}