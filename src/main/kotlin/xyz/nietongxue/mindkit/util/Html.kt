package xyz.nietongxue.mindkit.util

import kotlinx.html.li
import kotlinx.html.span
import kotlinx.html.stream.appendHTML
import kotlinx.html.ul
import kotlinx.html.unsafe
import xyz.nietongxue.mindkit.model.Node

fun Node.toHtml(): String {
    return buildString {
        appendHTML().span {
            text(this@toHtml.title)
            if (this@toHtml.children.isNotEmpty())
                ul {
                    this@toHtml.children.forEach {
                        li {
                             unsafe { + it.toHtml()}
                        }
                    }
                }
        }
    }

}