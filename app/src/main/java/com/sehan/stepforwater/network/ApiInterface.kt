package com.sehan.stepforwater.network

import com.sehan.stepforwater.model.MsgResponse
import com.sehan.stepforwater.network.URLManager.checkAppVersion
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET(checkAppVersion)
    fun checkAppVersion(
        @Query("versionCode") versionCode: Int,
        @Query("versionName") versionName: String
    ): Deferred<MsgResponse>
}