package xyz.nietongxue.mindkit.util

import org.reflections.Reflections
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation


fun <T : Any> scanForInstance(clazz: KClass<T>, prefix: String = "xyz.nietongxue.mindkit"): List<T> {
    val reflections = Reflections(prefix)
    val descriptors = reflections.getSubTypesOf(clazz.java)
    return descriptors.map {
        it.kotlin.objectInstance ?: it.newInstance()
    }.toList().filterNotNull().sortedByDescending {
        it::class.findAnnotation<Priority>()?.let {
            it.value
        } ?: 0
    }
}

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Priority(val value: Int)