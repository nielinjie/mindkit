package xyz.nietongxue.mindkit.application.htmlTable

import xyz.nietongxue.mindkit.application.AppDescriptor
import xyz.nietongxue.mindkit.application.Controller
import xyz.nietongxue.mindkit.model.Processor

class HtmlTable : AppDescriptor{
    //TODO xmind  里面的table，生成html（或者markdown？）table，比如可以copy到conf
    // - 生成html可以（至少可以以safari打开，然后copy到conf editor）
    // 后续看看是否可以展示在fx里，然后copy到clipboard。


    override val name: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val description: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val providedProcessors: List<Processor>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val controller: Controller
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

}