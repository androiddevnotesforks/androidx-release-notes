#!/usr/bin/env kotlin

@file:JvmName("Retry")
@file:CompilerOptions("-jvm-target", "11")
@file:Repository("https://repo.maven.apache.org/maven2")
@file:Repository("https://jcenter.bintray.com")
@file:Repository("https://jitpack.io")

import kotlin.time.Duration.Companion.seconds

val waitTime = 10.seconds

/**
 * Try for at most retryCount times to run the block without exception.
 */
fun <T> tryToGet(
    block: () -> T,
    retryCount: Int,
    failMessage: String,
    errorMessage: String
): T {
    repeat(retryCount) {
        val result = runCatching(block)
        if (result.isSuccess)
            return result.getOrThrow()
        println("$failMessage; attempting again in $waitTime")
        Thread.sleep(waitTime.inWholeMilliseconds)
    }
    error(errorMessage)
}
