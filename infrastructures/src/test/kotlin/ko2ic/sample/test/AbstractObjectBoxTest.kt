package ko2ic.sample.test

import io.objectbox.BoxStore
import ko2ic.sample.model.MyObjectBox
import org.junit.After
import org.junit.Before
import java.io.File


abstract class AbstractObjectBoxTest {

    protected lateinit var boxStoreDir: File
    protected lateinit var store: BoxStore

    @Before
    open fun setUp() {
        val tempFile = File.createTempFile("object-store-test", "")
        tempFile.delete()
        boxStoreDir = tempFile
        store = MyObjectBox.builder().directory(boxStoreDir).build()
    }

    @After
    open fun tearDown() {
        store.close()
        store.deleteAllFiles()

    }
}