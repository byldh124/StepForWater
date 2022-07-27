package com.sehan.stepforwater.network

import com.google.gson.JsonObject
import com.sehan.stepforwater.model.BaseResponse
import com.sehan.stepforwater.model.PersonalInfo
import org.json.JSONObject
import retrofit2.await

interface Repository {
    suspend fun checkAppVersion(versionCode: Int, versionName: String): UseCaseResult<BaseResponse>
    suspend fun signUp(jsonObject: JsonObject): UseCaseResult<BaseResponse>
    suspend fun registerToken(jsonObject: JsonObject): UseCaseResult<BaseResponse>
    suspend fun signIn(jsonObject: JsonObject): UseCaseResult<BaseResponse>
    suspend fun getSalt(jsonObject: JsonObject): UseCaseResult<BaseResponse>
}

class RepositoryImpl(private val api: ApiInterface) : Repository {
    override suspend fun checkAppVersion(
       versionCode: Int,
       versionName: String
    ): UseCaseResult<BaseResponse> {
        return try {
            val result = api.checkAppVersion(versionCode, versionName).await()
            UseCaseResult.Success(result)
        } catch (e: Exception) {
            UseCaseResult.Error(e)
        }
    }

    override suspend fun signUp(
        jsonObject: JsonObject
    ): UseCaseResult<BaseResponse> {
        return try {
            val result = api.signUp(jsonObject).await()
            UseCaseResult.Success(result)
        } catch (e: Exception){
            UseCaseResult.Error(e)
        }
    }

    override suspend fun registerToken(jsonObject: JsonObject): UseCaseResult<BaseResponse> {
        return try {
            val result = api.updateMessageToken(jsonObject).await()
            UseCaseResult.Success(result)
        } catch (e: Exception){
            UseCaseResult.Error(e)
        }
    }

    override suspend fun signIn(jsonObject: JsonObject): UseCaseResult<BaseResponse> {
        return try {
            val result = api.signIn(jsonObject).await()
            UseCaseResult.Success(result)
        } catch (e: Exception) {
            UseCaseResult.Error(e)
        }
    }

    override suspend fun getSalt(jsonObject: JsonObject): UseCaseResult<BaseResponse> {
        return try {
            val result = api.getSalt(jsonObject).await()
            UseCaseResult.Success(result)
        } catch (e:Exception){
            UseCaseResult.Error(e)
        }
    }
}