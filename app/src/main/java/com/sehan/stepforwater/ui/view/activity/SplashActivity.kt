package com.sehan.stepforwater.ui.view.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.sehan.stepforwater.R
import com.sehan.stepforwater.databinding.ActivitySplashBinding
import com.sehan.stepforwater.ui.viewmodel.SplashViewModel
import com.sehan.stepforwater.utils.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseCrashlytics.getInstance().log("Test Error")

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.activity = this

        startSplashAnimation()
        checkAppVersion()
    }

    private fun startSplashAnimation() {
        val fadeout = AnimationUtils.loadAnimation(this, R.anim.anim_splash_fade_out)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.anim_splash_fade_in)

        Glide.with(this).load(R.drawable.sw_splash_01).into(binding.ivSplash01)
        Glide.with(this).load(R.drawable.sw_splash_02).into(binding.ivSplash02)

        binding.ivSplash01.startAnimation(fadeout)
        binding.ivSplash02.startAnimation(fadeIn)

    }

    fun checkAppVersion() {
        viewModel.checkAppVersion()

        viewModel.appValid.observe(this) {
            when (it){
                Constants.ResponseCode.SUCCESS -> {
                    checkUserInfo()
                }
                Constants.ResponseCode.FAIL -> {

                }

                Constants.ResponseCode.INVALID_VALUE -> {

                }

                Constants.ResponseCode.NOT_EXIST -> {

                }
            }
        }
    }

    fun checkUserInfo() {

    }
}