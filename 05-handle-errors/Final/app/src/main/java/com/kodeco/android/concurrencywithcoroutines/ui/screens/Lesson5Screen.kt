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
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.IOException

@Composable
internal fun Lesson5Screen() {
  val coroutineScope = CoroutineScope(
    CoroutineName("Lesson 5 coroutine") +
        SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
      Log.e("Lesson5", "Exception handler", throwable)
    })

  Log.d("Lesson5", "thread: ${Thread.currentThread().name}")

  Column {
    Button(onClick = {
      coroutineScope.launch {
        Log.d(
          "Lesson5",
          "Coroutine inside launch on thread: ${Thread.currentThread().name}",
        )
        throw Exception("launch went wrong")
      }
    }) {
      Text("Throw exception from launch")
    }

    Button(onClick = {
      runBlocking {
        Log.d(
          "Lesson5",
          "Coroutine inside runBlocking on thread: ${Thread.currentThread().name}",
        )
        throw Exception("runBlocking went wrong")
      }
    }) {
      Text("Throw exception from runBlocking")
    }

    Button(onClick = {
      coroutineScope.launch {
        async {
          Log.d(
            "Lesson5",
            "Coroutine inside async on thread: ${Thread.currentThread().name}",
          )
          throw Exception("async went wrong")
        }
      }
    }) {
      Text("Throw exception from async")
    }

    Button(onClick = {
      coroutineScope.launch {
        launch { throw IOException("I/O error") }
        launch { throw IllegalArgumentException("Illegal argument error") }
      }
    }) {
      Text("Throw aggregated exceptions")
    }
  }
}
