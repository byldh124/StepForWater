package com.sehan.stepforwater.ui.view.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.sehan.stepforwater.R
import com.sehan.stepforwater.base.BaseDialog
import com.sehan.stepforwater.databinding.DialogLoadingBinding

class LoadingDialog(context: Context): BaseDialog(context) {

    lateinit var binding: DialogLoadingBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_loading, null, false)
        binding.dialog = this
        setContentView(binding.root)
        setCanceledOnTouchOutside(false)
        Glide.with(context).load(R.drawable.loading_black).into(binding.imgLoading)
    }
}