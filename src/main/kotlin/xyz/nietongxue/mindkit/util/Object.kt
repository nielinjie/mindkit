package xyz.nietongxue.mindkit.util

import java.util.*

fun <T : Any> Optional<T>.orNull(): T? {
    return runCatching { get() }.getOrNull()
}