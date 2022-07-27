package com.sehan.stepforwater.base

import android.app.Dialog
import android.content.Context
import com.sehan.stepforwater.R
import com.sehan.stepforwater.utils.firebase.SWCrash

open class BaseDialog :Dialog{

    constructor(context: Context) : super(context, R.style.DialogTheme){

    }

    constructor(context: Context, themeResId: Int) : super(context, themeResId){

    }

    fun logException(e: Exception) {
        SWCrash.logException(e)
    }
}