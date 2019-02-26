package xyz.nietongxue.mindkit.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import xyz.nietongxue.mindkit.model.repository.SimpleTextNode
import xyz.nietongxue.mindkit.model.source.InternalSource

internal class JsonStoreTest{

    @Test
    fun saveLoad(){
        val a = simple("test")
        val json = JsonStore.save(listOf(a))
        val b = JsonStore.load(json).first() as? SimpleTextNode
        assertThat(a.id).isEqualTo(b!!.id)
    }
}