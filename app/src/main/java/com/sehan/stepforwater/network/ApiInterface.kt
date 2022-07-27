package com.sehan.stepforwater.network

import com.google.gson.JsonObject
import com.sehan.stepforwater.model.BaseResponse
import com.sehan.stepforwater.model.PersonalInfo
import com.sehan.stepforwater.network.URLManager.checkAppVersion
import com.sehan.stepforwater.network.URLManager.getSalt
import com.sehan.stepforwater.network.URLManager.signIn
import com.sehan.stepforwater.network.URLManager.signUp
import com.sehan.stepforwater.network.URLManager.updateMessageToken
import kotlinx.coroutines.Deferred
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET(checkAppVersion)
    fun checkAppVersion(
        @Query("versionCode") versionCode: Int,
        @Query("versionName") versionName: String
    ): Deferred<BaseResponse>

    @POST(signUp)
    fun signUp(
        @Body jsonObject: JsonObject
    ): Deferred<BaseResponse>

    @POST(updateMessageToken)
    fun updateMessageToken(
        @Body jsonObject: JsonObject
    ): Call<BaseResponse>

    @POST(signIn)
    fun signIn(@Body jsonObject: JsonObject): Deferred<BaseResponse>

    @POST(getSalt)
    fun getSalt(@Body jsonObject: JsonObject): Deferred<BaseResponse>

}