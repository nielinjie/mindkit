package xyz.nietongxue.mindkit.util

import javafx.beans.property.Property
import javafx.beans.property.ReadOnlyObjectPropertyBase
import javafx.beans.property.ReadOnlyProperty
import tornadofx.onChange

class PairProperty<A, B>(val one: Property<A>, val two: Property<B>) : ReadOnlyObjectPropertyBase<Pair<A, B>>(), ReadOnlyProperty<Pair<A, B>> {
    init {
        one.onChange {
            super.fireValueChangedEvent()
        }
        two.onChange {
            super.fireValueChangedEvent()
        }
    }

    override fun get(): Pair<A, B> {
        return one.value to two.value
    }

    override fun getName(): String {
        return one.name + ", " + two.name
    }

    override fun getBean(): Any {
        return one.bean to two.bean
    }
}

fun <A, B> Property<A>.withAnother(two: Property<B>): PairProperty<A, B> = PairProperty(this, two)
