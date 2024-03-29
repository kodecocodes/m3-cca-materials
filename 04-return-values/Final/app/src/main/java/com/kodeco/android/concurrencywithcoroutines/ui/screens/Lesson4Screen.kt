/*
 * Copyright (c) 2024 Kodeco Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.kodeco.android.concurrencywithcoroutines.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds
import kotlin.time.measureTime
import kotlin.time.measureTimedValue

@Composable
internal fun Lesson4Screen() {
  val coroutineScope = rememberCoroutineScope()
  Column {
    Button(onClick = {
      val deferredResults = listOf(
        coroutineScope.async { doSomething(4) },
        coroutineScope.async { doSomething(2) },
      )
      coroutineScope.launch {
        val result = measureTimedValue { deferredResults.awaitAll() }
        Log.i("Lesson4", "Parallel action results: ${result.value}, took: ${result.duration}")
      }
    }) {
      Text(text = "Start parallel actions")
    }
    Button(onClick = {
      coroutineScope.launch {
        val result = measureTimedValue {
          listOf(
            doSomething(4),
            doSomething(2),
          )
        }
        Log.i("Lesson4", "Serial Action results: ${result.value}, took: ${result.duration}")
      }
    }) {
      Text(text = "Start serial actions")
    }
  }
}

private suspend fun doSomething(actionNumber: Int): Int {
  Log.i("Lesson4", "$actionNumber started")
  delay(actionNumber.seconds)
  Log.i("Lesson4", "$actionNumber finished")
  return actionNumber * 3
}
