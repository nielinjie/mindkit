package xyz.nietongxue.mindkit.util

import java.lang.Integer.max
import java.lang.Integer.min

class History<T> {
    private val history: MutableList<T> = mutableListOf()
    private var position: Int = -1
    fun clear(){
        history.clear()
        position = -1
    }
    fun add(value: T) {
        while (position < history.size - 1) {
            history.removeAt(history.size - 1)
        }
        history.add(value)
        position += 1
    }

    fun back() {
        position = max(0, position - 1)
    }

    fun forward() {
        position = min(history.size - 1, position + 1)
    }

    fun state(): HistoryState = HistoryState(position > 0, position < history.size - 1)

    fun current(): T = history[position]
}

data class HistoryState(val backEnabled: Boolean, val forwardEnabled: Boolean)