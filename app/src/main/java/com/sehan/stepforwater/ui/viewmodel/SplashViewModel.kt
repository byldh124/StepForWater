package com.sehan.stepforwater.ui.viewmodel

import android.os.Build
import androidx.lifecycle.MutableLiveData
import com.sehan.stepforwater.BuildConfig
import com.sehan.stepforwater.network.Repository
import com.sehan.stepforwater.network.UseCaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel(private val repository: Repository) : BaseViewModel() {

    val appValid = MutableLiveData<Int>()

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