package xyz.nietongxue.mindkit.view

import tornadofx.*

class MyView: View() {
    override val root = vbox {
        button("Press me")
        label("Waiting")
    }
}
class MyApp:App(){
    override val primaryView = MyView::class

}
fun main(args: Array<String>) {
    launch<MyApp>(args)
}