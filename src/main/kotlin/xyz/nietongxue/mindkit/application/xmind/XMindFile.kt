package xyz.nietongxue.mindkit.application.xmind

import java.io.InputStream
import java.util.zip.ZipFile

class XMindFile(val path: String) {
    fun content(): InputStream? {
        val zipFile = ZipFile(path)
        val entry = zipFile.getEntry("content.json")
        return entry?.let {
            zipFile.getInputStream(it)
        }
    }

    fun resource(resourceSrc: String): InputStream? {
        require(resourceSrc.startsWith("xap:"))
        val resourcePath = resourceSrc.drop(4)
        val zipFile = ZipFile(path)
        val entry = zipFile.getEntry(resourcePath)
        return entry?.let {
            zipFile.getInputStream(it)
        }
    }
}
