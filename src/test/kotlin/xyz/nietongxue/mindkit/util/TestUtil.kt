package xyz.nietongxue.mindkit.util

import xyz.nietongxue.mindkit.model.repository.SimpleTextNode
import xyz.nietongxue.mindkit.model.source.InternalSource

fun simple(id: String, ch: List<SimpleTextNode> = emptyList()) =
        SimpleTextNode(id, id, ch.toMutableList(), mutableListOf(), InternalSource)
