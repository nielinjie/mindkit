package xyz.nietongxue.mindkit.io

import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardWatchEventKinds
import java.nio.file.WatchService

class FileWatcher(file: File, watcherFun: (File, String) -> Unit) {
    var runIt = true
    var path: Path
    var dir: Boolean = false
    private fun Path.watch(): WatchService {
        //Create a watch service
        val watchService = this.fileSystem.newWatchService()

        //Register the service, specifying which events to watch
        register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.OVERFLOW, StandardWatchEventKinds.ENTRY_DELETE)

        //Return the watch service
        return watchService
    }

    init {

        if (file.isDirectory) {
            path = Paths.get(file.toURI())
            dir = true
        } else {
            path = Paths.get(file.parentFile.toURI())
            dir = false
        }


        val watcher = path.watch()
        Thread(
                Runnable {
                    while (this@FileWatcher.runIt) {
                        //The watcher blocks until an event is available
                        val key = watcher.take()

                        //Now go through each event on the folder
                        key.pollEvents().forEach { it ->
                            //Print output according to the event
                            if (dir) {
                                when (it.kind().name()) {
                                    "ENTRY_CREATE", "ENTRY_MODIFY", "ENTRY_DELETE" ->
                                        watcherFun(File(file, it.context().toString()), it.kind().name())
                                }
                            } else {
                                when (it.kind().name()) {
                                    "ENTRY_MODIFY" -> if (it.context().toString() == file.name
                                    ) {
                                        watcherFun(file, "ENTRY_MODIFY")
                                    }
                                }
                            }
                        }
                        //Call reset() on the key to watch for future events
                        key.reset()
                    }
                }
        ).start()

    }

}