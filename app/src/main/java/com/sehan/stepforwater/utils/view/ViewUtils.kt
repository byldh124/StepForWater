package com.sehan.stepforwater.utils.view

import android.app.Activity
import android.widget.Toast
import com.sehan.stepforwater.ui.viewmodel.BaseViewModel
import com.sehan.stepforwater.utils.SWLog

fun Activity.toast(msg:String){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Activity.log(msg:String){
    SWLog.e(this, msg)
}

fun BaseViewModel.log(msg: String){
    SWLog.e(this, msg)
}