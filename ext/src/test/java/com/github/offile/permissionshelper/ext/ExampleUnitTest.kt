package com.github.offile.permissionshelper.ext

import kotlinx.coroutines.*
import org.junit.Test

import org.junit.Assert.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    suspend fun test1() = suspendCoroutine<String> {
        it.context.ensureActive()
        if(it.context.isActive){
            it.resume("test1")
        }
    }

    suspend fun test2() = suspendCancellableCoroutine<String> {
        it.resume("test2")
    }

    @Test
    fun testqw() = runBlocking{
        GlobalScope.launch {
            val job1 = launch {
                for(i in 0..10){
                    Thread.sleep(200)
                    println(test1())
                }
            }
            val job2 = launch {
                for(i in 0..10){
                    Thread.sleep(200)
                    println(test2())
                }
            }
            delay(1000)
            job1.cancel()
            job2.cancel()
            println("job cancel")
        }.join()
    }
}