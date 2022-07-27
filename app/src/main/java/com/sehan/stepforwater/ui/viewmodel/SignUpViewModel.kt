package com.sehan.stepforwater.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.sehan.stepforwater.model.PersonalInfo
import com.sehan.stepforwater.network.Repository
import com.sehan.stepforwater.network.SingleLiveEvent
import com.sehan.stepforwater.network.UseCaseResult
import com.sehan.stepforwater.utils.SWUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.security.SecureRandom

class SignUpViewModel(private val repository: Repository) : BaseViewModel() {
    val signUpCode = SingleLiveEvent<Int>()
    val tokenCode = SingleLiveEvent<Int>()

    fun signUp(id: String, hashPw: String, name: String, salt: String) {
        launch {
            val response = withContext(Dispatchers.IO) {
                val jsonObject = JsonObject()
                jsonObject.addProperty("id", id)
                jsonObject.addProperty("hashPw", hashPw)
                jsonObject.addProperty("name", name)
                jsonObject.addProperty("salt", salt)
                repository.signUp(jsonObject)
            }
            when (response) {
                is UseCaseResult.Success -> {
                    val code = response.data.code
                    signUpCode.value = code
                }

                is UseCaseResult.Error -> {
                    response.exception.message?.let {
                        logException(it)
                    }
                }
            }
        }
    }

    fun registerToken(id: String, token: String) {
        launch {
            val response = withContext(Dispatchers.IO) {
                val jsonObject = JsonObject()
                jsonObject.addProperty("id", id)
                jsonObject.addProperty("token", token)
                repository.registerToken(jsonObject)
            }

            when (response){
                is UseCaseResult.Success -> {
                    val code = response.data.code
                    tokenCode.value = code
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