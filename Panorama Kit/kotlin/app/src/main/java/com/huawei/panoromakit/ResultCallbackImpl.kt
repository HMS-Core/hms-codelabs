/*
* Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

* Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package com.huawei.panoromakit

import android.content.Context
import com.huawei.hms.panorama.PanoramaInterface.ImageInfoResult
import com.huawei.hms.support.api.client.ResultCallback

class ResultCallbackImpl(context: Context) : ResultCallback<ImageInfoResult> {

    private var context: Context? = null

    init {
        this.context = context
    }


    override fun onResult(result: ImageInfoResult?) {
        result?.let {
            if (result.status.isSuccess) {
                val intent = result.imageDisplayIntent
                intent?.let {
                    context?.startActivity(intent)
                }
            }
        }

    }

}