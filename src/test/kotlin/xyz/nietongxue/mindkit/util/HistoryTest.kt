package xyz.nietongxue.mindkit.util

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.assertj.core.api.Assertions.*


internal class HistoryTest {

    @Test
    fun add() {
        val history = History<String>()
        history.add("a")
        //A
        assertThat(history.current()).isEqualTo("a")
        assertEquals(history.state().backEnabled,false)
        assertEquals(history.state().forwardEnabled,false)
        history.add("b")
        //a,B
        assertEquals(history.current(),"b")
        assertEquals(history.state().backEnabled,true)
        assertEquals(history.state().forwardEnabled,false)
        history.back()
        //A,b
        assertEquals(history.current(),"a")
        assertEquals(history.state().backEnabled,false)
        assertEquals(history.state().forwardEnabled,true)
        history.forward()
        //a,B
        assertEquals(history.current(),"b")
        assertEquals(history.state().backEnabled,true)
        assertEquals(history.state().forwardEnabled,false)
        history.back()
        //A,b
        assertEquals(history.current(),"a")
        assertEquals(history.state().backEnabled,false)
        assertEquals(history.state().forwardEnabled,true)
        history.add("c")
        //a,C
        assertEquals(history.current(),"c")
        assertEquals(history.state().backEnabled,true)
        assertEquals(history.state().forwardEnabled,false)
        history.back()
        assertEquals(history.current(),"a")

    }

//    @Test
//    fun back() {
//    }
//
//    @Test
//    fun forward() {
//    }
//
//    @Test
//    fun state() {
//    }
//
//    @Test
//    fun current() {
//    }
}