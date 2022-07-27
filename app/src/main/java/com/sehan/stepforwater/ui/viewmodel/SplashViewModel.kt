package com.sehan.stepforwater.ui.viewmodel

import com.sehan.stepforwater.BuildConfig
import com.sehan.stepforwater.network.Repository
import com.sehan.stepforwater.network.SingleLiveEvent
import com.sehan.stepforwater.network.UseCaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class SplashViewModel(private val repository: Repository) : BaseViewModel() {

    val appValid = SingleLiveEvent<Int>()

    fun checkAppVersion() {
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.checkAppVersion(BuildConfig.VERSION_CODE, BuildConfig.VERSION_NAME)
            }

            when (response) {
                is UseCaseResult.Success -> {
                    val code = response.data.code
                    appValid.value = code
                }

                is UseCaseResult.Error -> {
                   response.exception.message?.let{
                       logException(it)
                   }
                }
            }
        }

    }
}