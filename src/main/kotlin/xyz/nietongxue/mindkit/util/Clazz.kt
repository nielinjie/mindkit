package xyz.nietongxue.mindkit.util

import org.reflections.Reflections
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation

val cache :MutableMap<Any,Any> = mutableMapOf()

fun <T : Any> scanForInstance(clazz: KClass<T>, prefix: String = "xyz.nietongxue.mindkit"): List<T> {
    return cache.computeIfAbsent((clazz to prefix)) {
        Reflections(prefix).getSubTypesOf(clazz.java).map {
            it.kotlin.objectInstance ?: it.newInstance()
        }.toList().filterNotNull().sortedByDescending {
            it::class.findAnnotation<Priority>()?.let {
                it.value
            } ?: 0
        }
    } as List<T>
}

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Priority(val value: Int)