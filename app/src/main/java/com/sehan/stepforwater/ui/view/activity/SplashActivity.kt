package com.sehan.stepforwater.ui.view.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.sehan.stepforwater.R
import com.sehan.stepforwater.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.activity = this

        startSplashAnimation()
        checkAppVersion()
    }

    private fun startSplashAnimation(){
        val fadeout = AnimationUtils.loadAnimation(this, R.anim.anim_splash_fade_out)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.anim_splash_fade_in)

        Glide.with(this).load(R.drawable.sw_splash_01).into(binding.ivSplash01);
        Glide.with(this).load(R.drawable.sw_splash_02).into(binding.ivSplash02);

        binding.ivSplash01.startAnimation(fadeout)
        binding.ivSplash02.startAnimation(fadeIn)

    }

    fun checkAppVersion() {

    }

    fun checkUserInfo() {

    }
}