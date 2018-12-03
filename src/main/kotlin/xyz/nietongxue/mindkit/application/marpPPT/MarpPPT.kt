package xyz.nietongxue.mindkit.application.marpPPT

import xyz.nietongxue.mindkit.application.AppDescriptor
import xyz.nietongxue.mindkit.model.Processor
import xyz.nietongxue.mindkit.model.TemplateProcessor

object MarpPPT : AppDescriptor {
    override val processors: List<Processor> by lazy{
        listOf(
        object : TemplateProcessor(MarpPPT::class.java.getResource("/marpSlide.twig").readText()) {
            override val app: AppDescriptor by lazy{
                MarpPPT
            }
            override val brief = "Marp Slides 模板"
            override val description = this.templateString

        })
    }

    override val name: String= "Marp PPT"
    override val description:String = "Marp PPT"
    override val view: ProcessorView by lazy{
        ProcessorView()
    }
    override val controller = view

}