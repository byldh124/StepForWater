package com.sehan.stepforwater.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.gson.JsonObject
import com.sehan.stepforwater.application.App
import com.sehan.stepforwater.di.ServiceBuilder
import com.sehan.stepforwater.model.BaseResponse
import com.sehan.stepforwater.model.BoolResponse
import com.sehan.stepforwater.network.ApiInterface
import com.sehan.stepforwater.utils.SWLog
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response


class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
        if (App.id.isNotEmpty()) {
            val request = ServiceBuilder.buildService(ApiInterface::class.java)
            val jsonObject = JsonObject()
            jsonObject.addProperty("id", App.id)
            jsonObject.addProperty("token", token)
            request.updateMessageToken(jsonObject)
                .enqueue(object : retrofit2.Callback<BaseResponse> {
                    override fun onResponse(
                        call: Call<BaseResponse>,
                        response: Response<BaseResponse>
                    ) {
                        SWLog.e(
                            this@MyFirebaseMessagingService,
                            "sendRegistrationToServer()-onResponse() response = ${response.isSuccessful}"
                        )
                    }

                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                        SWLog.e(
                            this@MyFirebaseMessagingService,
                            "sendRegistrationToServer()-onFailure() throwable = ${t.message}"
                        )
                    }
                })
        }
    }
}