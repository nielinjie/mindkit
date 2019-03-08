package xyz.nietongxue.mindkit.util

import java.util.*

fun <T:Any> Optional<T>.orNull():T?{
    return this.let {
        if(it.isPresent)
            it.get()
        else
            null
    }
}