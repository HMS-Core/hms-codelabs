/*
Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.huawei.logger

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import java.text.SimpleDateFormat
import java.util.Date

class LogView : AppCompatTextView, LogNode {
    var next: LogNode? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    override fun println(
        priority: Int,
        tag: String?,
        msg: String?,
        tr: Throwable?
    ) {
        val formatter = SimpleDateFormat.getTimeInstance()
        val curDate = Date(System.currentTimeMillis())
        val str = formatter.format(curDate)
        val outputBuilder = StringBuilder()
            .append(str)
            .append(" ")
            .append(msg)
            .append("\r\n")
        (context as Activity).runOnUiThread {
            appendToLog(outputBuilder.toString())
        }
        next?.println(priority, tag, msg, tr)
    }

    private fun appendToLog(s: String) {
        append("\n" + s)
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }
}