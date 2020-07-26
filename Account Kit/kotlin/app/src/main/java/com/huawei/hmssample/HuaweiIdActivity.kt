/*
 * Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.huawei.hmssample

import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.huawei.hms.common.ApiException
import com.huawei.hms.support.hwid.HuaweiIdAuthManager
import com.huawei.hms.support.hwid.request.HuaweiIdAuthExtendedParams
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper
import com.huawei.hms.support.hwid.service.HuaweiIdAuthService
import com.huawei.hmssample.common.ICallBack
import com.huawei.logger.Log.i
import com.huawei.logger.LoggerActivity
import kotlinx.android.synthetic.main.activity_huaweiid.hwid_signInCode
import kotlinx.android.synthetic.main.activity_huaweiid.hwid_signin
import kotlinx.android.synthetic.main.activity_huaweiid.hwid_signout

/**
 * Codelab
 * Demonstration of HuaweiId
 */
class HuaweiIdActivity : LoggerActivity() {

    companion object {
        // Log tag
        const val TAG = "HuaweiIdActivity"
    }

    private lateinit var mAuthParam: HuaweiIdAuthParams
    private lateinit var mAuthManager: HuaweiIdAuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_huaweiid)
        hwid_signin.setOnClickListener { signIn() }
        hwid_signout.setOnClickListener { signOut() }
        hwid_signInCode.setOnClickListener { signInCode() }

        //sample log Please ignore
        addLogFragment()
        //Initialize the HuaweiIdSignInClient object by calling the getClient method of HuaweiIdSignIn
        mAuthParam = HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM).apply {
            setIdToken()
            setAccessToken()
        }.createParams()
        mAuthManager = HuaweiIdAuthManager.getService(this, mAuthParam)
    }

    /**
     * Codelab Code
     * Pull up the authorization interface by getSignInIntent
     */
    private fun signIn() {
        startActivityForResult(mAuthManager.signInIntent, Constant.REQUEST_SIGN_IN_LOGIN)
    }

    private fun signInCode() {
        mAuthParam = HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM).apply {
            setProfile()
            setAuthorizationCode()
        }.createParams()
        mAuthManager = HuaweiIdAuthManager.getService(this, mAuthParam)
        startActivityForResult(mAuthManager.signInIntent, Constant.REQUEST_SIGN_IN_LOGIN_CODE)
    }

    /**
     * Codelab Code
     * sign Out by signOut
     */
    private fun signOut() {
        mAuthManager.signOut().addOnSuccessListener { printLog("signOut Success") }
            .addOnFailureListener { printLog("signOut fail") }
    }

    private fun validateIdToken(idToken: String?) {
        if (idToken.isNullOrEmpty()) {
            printLog("ID Token is empty")
            return
        }
        try {
            val idTokenParser = IDTokenParser()
            idTokenParser.verify(idToken, object : ICallBack {
                override fun onSuccess() {
                    printLog("id Token Validate Success.")
                }

                override fun onSuccess(result: String?) {
                    if (!result.isNullOrEmpty()) {
                        printLog("id Token Validate Success, verify signature: $result")
                    } else {
                        printLog("Id token validate failed.")
                    }
                }

                override fun onFailed() {
                    printLog("Id token validate failed.")
                }
            })
        } catch (e: Exception) {
            printLog("id Token validate failed. ${e.javaClass.simpleName}")
        } catch (e: Error) {
            printLog("id Token validate failed. ${e.javaClass.simpleName}")
            if (Build.VERSION.SDK_INT < 23) {
                printLog("android SDK Version is not support. Current version is: ${Build.VERSION.SDK_INT}")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            Constant.REQUEST_SIGN_IN_LOGIN -> {
                //login success
                //get user message by parseAuthResultFromIntent
                val authHuaweiIdTask = HuaweiIdAuthManager.parseAuthResultFromIntent(data)
                if (authHuaweiIdTask.isSuccessful) {
                    val huaweiAccount = authHuaweiIdTask.result
                    printLog("${huaweiAccount.displayName} signIn success ")
                    printLog("AccessToken: ${huaweiAccount.accessToken}")
                    validateIdToken(huaweiAccount.idToken)
                } else {
                    printLog("signIn failed: ${(authHuaweiIdTask.exception as ApiException).statusCode}")
                }
            }

            Constant.REQUEST_SIGN_IN_LOGIN_CODE -> {
                //login success
                val authHuaweiIdTask = HuaweiIdAuthManager.parseAuthResultFromIntent(data)
                if (authHuaweiIdTask.isSuccessful) {
                    val huaweiAccount = authHuaweiIdTask.result
                    printLog("signIn get code success.")
                    printLog("ServerAuthCode: ${huaweiAccount.authorizationCode}")
                    /**** english doc:For security reasons,
                     * the operation of changing the code to an AT must be performed on your server.
                     * The code is only an example and cannot be run.  */
                } else {
                    printLog("signIn get code failed: ${(authHuaweiIdTask.exception as ApiException).statusCode}" )
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun printLog(msg: String) {
        i(TAG, msg)
    }

    /**
     * sample log Please ignore
     */
    private fun addLogFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = LogFragment()
        transaction.replace(R.id.framelog, fragment)
        transaction.commit()
    }

}