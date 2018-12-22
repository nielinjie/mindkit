package xyz.nietongxue.mindkit.application.marpPPT

import javafx.scene.Parent
import xyz.nietongxue.mindkit.actions.Action
import xyz.nietongxue.mindkit.actions.ActionDescriptor
import xyz.nietongxue.mindkit.application.TemplateFunction
import xyz.nietongxue.mindkit.application.xmind.Node

object MarpPPTAction : ActionDescriptor {
    override fun actions(node: Node): List<Action> {
        return listOf(
                object : Action {
                    val con = ProcessView()
                    override val brief: String = "Markdown"
                    override val description: String = "生成Marp兼容的Markdown，以便生成PPT"

                    override fun action(node: Node) {
                        con.controller.function =  object : TemplateFunction(MarpPPTAction::class.java.getResource("/marpSlide.twig").readText()){}
                        con.process(node)
                    }

                    override fun view(node: Node): Parent? {
                        return con.view.root
                    }

                }
        )
    }

}