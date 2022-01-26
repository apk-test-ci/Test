/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp

// 使用hilt必须声明Application注入入口点
@HiltAndroidApp
class MainApplication : Application(), Configuration.Provider {


    override fun onCreate() {
        super.onCreate()
        context = this
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setMinimumLoggingLevel(if (BuildConfig.DEBUG) android.util.Log.DEBUG else android.util.Log.ERROR)
            .build()


    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
}
