package com.sehan.stepforwater.ui.viewmodel

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.sehan.stepforwater.model.PersonalInfo
import com.sehan.stepforwater.network.Repository
import com.sehan.stepforwater.network.SingleLiveEvent
import com.sehan.stepforwater.network.UseCaseResult
import com.sehan.stepforwater.utils.Constants
import com.sehan.stepforwater.utils.SWLog
import com.sehan.stepforwater.utils.view.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInViewModel(private val repository: Repository): BaseViewModel() {

    val saltStatus = SingleLiveEvent<Int>()
    val salt = SingleLiveEvent<String>()

    val user = SingleLiveEvent<PersonalInfo>()
    val userStatus = SingleLiveEvent<Int>()

    fun getSalt(id: String){
        launch {
            val response = withContext(Dispatchers.IO){
                val jsonObject = JsonObject()
                jsonObject.addProperty("id", id)
                repository.getSalt(jsonObject)
            }

            when (response){
                is UseCaseResult.Success -> {
                    val code = response.data.code
                    if (code == Constants.ResponseCode.SUCCESS){
                        val result = response.data.body.asString
                        salt.value = result
                    } else {
                        saltStatus.value = code
                    }
                }

                is UseCaseResult.Error -> {
                    response.exception.message?.let {
                        logException(it)
                    }
                }
            }
        }

    }


    fun signIn(id: String, hashPw: String){
        launch {
            val response = withContext(Dispatchers.IO){
                val jsonObject = JsonObject()
                jsonObject.addProperty("id", id)
                jsonObject.addProperty("hashPw" , hashPw)

                repository.signIn(jsonObject)
            }

            when(response){
                is UseCaseResult.Success -> {
                    val code = response.data.code

                    if (code == Constants.ResponseCode.SUCCESS){
                        val result = response.data.body.asJsonObject
                        user.value = Gson().fromJson(result, PersonalInfo::class.java)
                    } else {
                        userStatus.value = code
                    }
                }

                is UseCaseResult.Error -> {
                    response.exception.message?.let {
                        logException(it)
                    }
                }
            }
        }
    }
}