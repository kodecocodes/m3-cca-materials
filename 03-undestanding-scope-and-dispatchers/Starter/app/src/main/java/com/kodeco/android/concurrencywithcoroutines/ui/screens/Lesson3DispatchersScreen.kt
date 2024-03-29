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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun Lesson3DispatchersScreen(
  onNavigateBack: () -> Unit,
) {
  Column {
    Text(text = "Launch 100 coroutines on dispatcher:")
    Button(onClick = { startCoroutines(Dispatchers.IO) }) {
      Text(text = "I/O")
    }
    Button(onClick = { startCoroutines(Dispatchers.Default) }) {
      Text(text = "Default")
    }
    Button(onClick = { startCoroutines(Dispatchers.Main) }) {
      Text(text = "Main")
    }
    Button(onClick = { startCoroutinesWithContext() }) {
      Text(text = "I/O with Main context")
    }
    Button(onClick = onNavigateBack) {
      Text(text = "Go back to menu")
    }
  }
}

private fun startCoroutines(dispatcher: CoroutineDispatcher) {
  Log.d("Start", Thread.currentThread().name)
  CoroutineScope(dispatcher).launch {
    repeat(100) { index ->
      Log.i("Launching coroutine #$index", Thread.currentThread().name)
      launch {
        Log.i("Running coroutine #$index", Thread.currentThread().name)
        delay(10.milliseconds)
      }
    }
  }
}

private fun startCoroutinesWithContext() {
  CoroutineScope(Dispatchers.IO).launch {
    repeat(100) { index ->
      Log.i("Launching coroutine #$index", Thread.currentThread().name)
      launch {
        delay(10.milliseconds)
        withContext(Dispatchers.Main) {
          Log.i("Running coroutine #$index", Thread.currentThread().name)
        }
      }
    }
  }
}
