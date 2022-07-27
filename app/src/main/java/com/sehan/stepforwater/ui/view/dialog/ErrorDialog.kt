package com.sehan.stepforwater.ui.view.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.sehan.stepforwater.R
import com.sehan.stepforwater.base.BaseDialog
import com.sehan.stepforwater.databinding.DialogErrorBinding

class ErrorDialog(context: Context, var msg: String, var eventListener: EventListener) :
    BaseDialog(context) {

    lateinit var binding: DialogErrorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_error,
            null,
            false
        )

        binding.dialog = this

        setContentView(binding.root)
    }

    fun confirm(){
        cancel()
    }

    override fun cancel() {
        super.cancel()
        eventListener.onClick()
    }


    interface EventListener {
        fun onClick()
    }
}