package xyz.nietongxue.mindkit.view

import xyz.nietongxue.mindkit.model.Filter


enum class FilterResult {
    CHILD_AND_SELF, SELF, CHILD, NONE;

    fun add(b: FilterResult): FilterResult {
        return when (this) {
            CHILD_AND_SELF -> CHILD_AND_SELF
            SELF -> when (b) {
                CHILD_AND_SELF -> CHILD_AND_SELF
                SELF -> SELF
                CHILD -> CHILD_AND_SELF
                NONE -> SELF
            }
            CHILD -> when (b) {
                CHILD_AND_SELF -> CHILD_AND_SELF
                SELF -> CHILD_AND_SELF
                CHILD -> CHILD
                NONE -> CHILD
            }
            NONE -> b
        }
    }
}

interface ViewRange {
    fun markVisible(root: ViewNode)
}

class ModelFilterRange(val modelFilter: Filter) : ViewRange {
    override fun markVisible(root: ViewNode) {
        root.iteratorViewNode {
            if (modelFilter(it.node)) {
                it.filterResult = it.filterResult.add(FilterResult.SELF)
            }
        }
    }
}

class FocusRange(val focus: ViewNode) : ViewRange {
    override fun markVisible(root: ViewNode) {
        focus.filterResult = focus.filterResult.add(FilterResult.SELF)

        focus.children.forEach {
            it.filterResult = it.filterResult.add(FilterResult.SELF)
        }

        //brothers
        focus.findParent(root)?.also {
            it.children.forEach {
                it.filterResult = it.filterResult.add(FilterResult.SELF)
            }
        }

    }

}