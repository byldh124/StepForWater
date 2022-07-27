package com.sehan.stepforwater.ui.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.sehan.stepforwater.R
import com.sehan.stepforwater.application.App
import com.sehan.stepforwater.base.BaseActivity
import com.sehan.stepforwater.databinding.ActivitySplashBinding
import com.sehan.stepforwater.ui.view.dialog.ErrorDialog.EventListener
import com.sehan.stepforwater.ui.viewmodel.SplashViewModel
import com.sehan.stepforwater.utils.Constants
import com.sehan.stepforwater.utils.SWLog
import com.sehan.stepforwater.utils.SWUtils
import com.sehan.stepforwater.utils.view.log
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    private val log = this.javaClass.simpleName.trim()

    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModel()
    var toIntent: Intent? = null
    private var isAnimEnd = false


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

        fadeIn.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                isAnimEnd = true
                if (toIntent != null && !this@SplashActivity.isFinishing) {
                    startActivity(toIntent)
                    finish()
                    overridePendingTransition(android.R.anim.fade_in, 0)
                }
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }
        })

        Glide.with(this).load(R.drawable.sw_splash_01).into(binding.ivSplash01)
        Glide.with(this).load(R.drawable.sw_splash_02).into(binding.ivSplash02)

        binding.ivSplash01.startAnimation(fadeout)
        binding.ivSplash02.startAnimation(fadeIn)
    }

    private fun checkAppVersion() {

        viewModel.checkAppVersion()

        log("checkAppVersion() call")

        viewModel.appValid.observe(this) {

            log("checkAppVersion() , responseCode = $it")

            when (it) {
                Constants.ResponseCode.SUCCESS -> {
                    checkUserInfo()
                }
                Constants.ResponseCode.FAIL -> {
                    showError("네트워크 연결에 실패했습니다.", object : EventListener {
                        override fun onClick() {
                            exitApp()
                        }
                    })
                }

                Constants.ResponseCode.INVALID_VALUE -> {
                    showError("버전정보가 낮습니다.\n최신 버전으로 업데이트가 필요합니다.", object : EventListener {
                        override fun onClick() {
                            updateApp()
                        }
                    })
                }

                Constants.ResponseCode.NOT_EXIST -> {
                    showError("해당 버전 정보가 존재하지 않습니다.", object : EventListener {
                        override fun onClick() {
                            exitApp()
                        }
                    })
                }
            }
        }
    }

    private fun checkUserInfo() {

        val token = App.prefs.getString(Constants.USER_ACCESS_TOKEN)
        toIntent = if (token.isNullOrEmpty()) {
            Intent(this, SignInActivity::class.java)
        } else {
            Intent(this, MainActivity::class.java)
        }

        if (isAnimEnd){
            startActivity(toIntent)
            finish()
            overridePendingTransition(android.R.anim.fade_in, 0)
        }
    }

    fun exitApp() {
        this.finish()
    }

    fun updateApp() {
        Toast.makeText(this, "구글 플레이 스토어로 이동", Toast.LENGTH_SHORT).show()
        this.finish()
    }
}