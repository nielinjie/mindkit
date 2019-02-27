package xyz.nietongxue.mindkit.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import xyz.nietongxue.mindkit.model.repository.NodeRecorder


class NodeTest {

    @Test
    fun testFind() {

        val root = simple("a", listOf(
                simple("a1"),
                simple("a2", listOf(
                        simple("a21")
                )
                )
        ))
        val find = root.findById("a1")
        assertThat(find!!.id).isEqualTo("a1")
        assertThat(root.findById("a")!!.id).isEqualTo("a")
        assertThat(root.findById("a1")!!.id).isEqualTo("a1")
        assertThat(root.findById("b")).isNull()
        assertThat(root.findById("a2")!!.id).isEqualTo("a2")
        assertThat(root.findById("a21")!!.id).isEqualTo("a21")

    }

    @Test
    fun testRecorder() {
        fun sR(id: String, parentId: String) = NodeRecorder(id, id, emptyList(), parentId)
        val recorders = listOf(
                sR("a", "_"),
                sR("a1", "a")
        )
        val node = NodeRecorder.toNodes(recorders)
        assertThat(node).isNotEmpty.size().isEqualTo(1)
        assertThat(node.first().first.children).size().isEqualTo(1)
        assertThat(node.first().first.children).first().satisfies {
            it.id == "a1"
            it.children.isEmpty()
        }
    }

    @Test
    fun testRecorder2() {
        val root = simple("a", listOf(
                simple("a1"),
                simple("a2", listOf(
                        simple("a21")
                )
                )
        ))
        val recorders = NodeRecorder.fromNodes(root)
        val node = NodeRecorder.toNodes(recorders)
        assertThat(node.first().first).isEqualTo(root)
    }
}