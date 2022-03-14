package com.example.dogceochallenge.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun applyWithDelay(delayInMillis: Long, block: () -> Unit) {
    CoroutineScope(Dispatchers.Main).launch {
        withContext(Dispatchers.IO) { delay(delayInMillis) }
        block()
    }
}
