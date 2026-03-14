package com.vroff.doubletape.storage
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import app.cash.turbine.test
import com.vroff.domain.storage.DoubleTapeDataStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

@OptIn(ExperimentalCoroutinesApi::class)
class DoubleTapeDataStoreImplTest {

    @get:Rule
    val tmpFolder = TemporaryFolder()

    private lateinit var storage: DoubleTapeDataStoreImpl
    private val testScope = TestScope(UnconfinedTestDispatcher())

    @Before
    fun setup() {
        val testDataStore = PreferenceDataStoreFactory.create(
            scope = testScope,
            produceFile = { File(tmpFolder.newFolder(), "test.preferences_pb") }
        )
        storage = DoubleTapeDataStoreImpl(testDataStore)
    }

    @Test
    fun `saveString and getStringOrDefault should return correct value`() = runTest {
        val key = DoubleTapeDataStore.Keys.Configuration
        val value = "DoubleTapeValue"

        storage.saveString(key, value)

        storage.getStringOrDefault(key, "default").test {
            assertEquals(value, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `saveSerialize and getSerialize should work with objects`() = runTest {
        @kotlinx.serialization.Serializable
        data class TestModel(val id: Int, val name: String)

        val key = DoubleTapeDataStore.Keys.Configuration
        val model = TestModel(1, "Test")

        storage.saveSerialize(key, model, TestModel.serializer())

        storage.getSerialize(key, TestModel.serializer()).test {
            assertEquals(model, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}