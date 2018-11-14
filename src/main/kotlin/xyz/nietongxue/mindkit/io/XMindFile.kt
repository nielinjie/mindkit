package xyz.nietongxue.mindkit.io

import java.io.File
import java.io.InputStream
import java.util.zip.ZipFile

class XMindFile(val path: String) {
    fun content(): InputStream {
        val zipFile = ZipFile(path)
        val entry = zipFile.getEntry("content.json")
        return zipFile.getInputStream(entry)
    }
}
