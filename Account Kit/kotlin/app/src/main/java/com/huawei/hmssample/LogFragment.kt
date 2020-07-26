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
package com.huawei.hmssample

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener
import android.widget.ScrollView
import com.huawei.logger.LogView

class LogFragment : Fragment() {

    lateinit var logView: LogView
    private lateinit var mScrollView: ScrollView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val result = inflateViews()

        logView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(s: Editable) {
                mScrollView.post { mScrollView.fullScroll(ScrollView.FOCUS_DOWN) }
            }
        })

        val gestureDetector = GestureDetector(activity,object : SimpleOnGestureListener(){
            override fun onDoubleTap(e: MotionEvent): Boolean {
                logView.text = ""
                return true
            }
        })
        
        logView.setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
        return result
    }

    private fun inflateViews(): View {
        mScrollView = ScrollView(activity)
        mScrollView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        logView = LogView(activity)
        logView.isClickable = true
        mScrollView.addView(
            logView, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
        return mScrollView
    }

}