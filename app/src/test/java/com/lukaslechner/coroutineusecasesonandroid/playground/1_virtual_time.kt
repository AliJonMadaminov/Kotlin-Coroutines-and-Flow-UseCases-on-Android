package com.lukaslechner.coroutineusecasesonandroid.playground

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SystemUnderTest {
    suspend fun functionWithDelay(): Int {
        delay(1000)
        return 42
    }
}

class TestClass {

    @Test
    fun `functionWithDelay() should return 42`() {
        val systemUnderTest = SystemUnderTest()
        val result = runBlocking { systemUnderTest.functionWithDelay() }
        assertEquals(42, result)
    }
}