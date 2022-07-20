package com.sehan.stepforwater.network

import com.sehan.stepforwater.model.MsgResponse

interface Repository {
    suspend fun checkAppVersion(versionCode: Int, versionName: String): UseCaseResult<MsgResponse>
}

class RepositoryImpl(private val api: ApiInterface) : Repository {
    override suspend fun checkAppVersion(
        versionCode: Int,
        versionName: String
    ): UseCaseResult<MsgResponse> {
        return try {
            val result = api.checkAppVersion(versionCode, versionName).await()
            UseCaseResult.Success(result)
        } catch (e: Exception) {
            UseCaseResult.Error(e)
        }
    }
}