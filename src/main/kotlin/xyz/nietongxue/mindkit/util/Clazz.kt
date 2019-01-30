package xyz.nietongxue.mindkit.util

import org.reflections.Reflections
import kotlin.concurrent.getOrSet
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation


val cache: MutableMap<Any, Any> = mutableMapOf()

fun <T : Any> scanForInstance(clazz: KClass<T>, prefix: String = "xyz.nietongxue.mindkit"): List<T> {
    return synchronized(cache) {
        cache.computeIfAbsent((clazz to prefix)) {
//            println(clazz.simpleName)
            Reflections(prefix).getSubTypesOf(clazz.java).asSequence().filterNot {
                it.kotlin.findAnnotation<Disabled>() != null
            }.sortedByDescending {
                it.kotlin.findAnnotation<Priority>()?.value ?: 0
            }.map {
                it.kotlin.objectInstance ?: it.newInstance()
            }.toList().filterNotNull().toList()
        } as List<T>
    }
}

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Priority(val value: Int = 10)


@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Disabled