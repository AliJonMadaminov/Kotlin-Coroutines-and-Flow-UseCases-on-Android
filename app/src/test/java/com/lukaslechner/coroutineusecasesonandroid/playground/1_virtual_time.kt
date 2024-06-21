package com.lukaslechner.coroutineusecasesonandroid.playground

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SystemUnderTest {
    suspend fun functionWithDelay(): Int {
        delay(1000)
        return 42
    }
}

class TestClass {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `functionWithDelay() should return 42`() = runTest {
        val realTimeStart = System.currentTimeMillis()
        val virtualTimeStart = currentTime
        val systemUnderTest = SystemUnderTest()
        val result = systemUnderTest.functionWithDelay()
        assertEquals(42, result)
        val realTimeDuration = System.currentTimeMillis() - realTimeStart
        val virtualTimeDuration = currentTime - virtualTimeStart
        println("Real time duration: $realTimeDuration")
        println("Virtual time duration: $virtualTimeDuration")
    }
}