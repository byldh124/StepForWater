package com.sehan.stepforwater.ui.view.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sehan.stepforwater.R
import com.sehan.stepforwater.application.App
import com.sehan.stepforwater.base.BaseActivity
import com.sehan.stepforwater.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this

        binding.tv.text = "Id : ${App.id}\n" + "Name : ${App.name}"
    }
}