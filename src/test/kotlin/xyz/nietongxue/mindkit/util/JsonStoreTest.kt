package xyz.nietongxue.mindkit.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import xyz.nietongxue.mindkit.model.SimpleTextNode

internal class JsonStoreTest{

    @Test
    fun saveLoad(){
        val a = SimpleTextNode("test","test", mutableListOf(), mutableListOf())
        val json = JsonStore.save(listOf(a))
        val b = JsonStore.load(json).first()
        assertThat(a).isEqualTo(b)
    }
}