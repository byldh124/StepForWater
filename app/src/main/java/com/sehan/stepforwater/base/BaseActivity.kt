package com.sehan.stepforwater.base

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.sehan.stepforwater.ui.view.activity.MainActivity
import com.sehan.stepforwater.ui.view.dialog.ErrorDialog
import com.sehan.stepforwater.ui.view.dialog.LoadingDialog
import com.sehan.stepforwater.utils.SWLog
import com.sehan.stepforwater.utils.view.log

open class BaseActivity : AppCompatActivity() {

    var errorDialog: ErrorDialog? = null
    var loadingDialog: LoadingDialog? = null

    fun showError(msg: String, eventListener: ErrorDialog.EventListener){

        log("showError , msg = $msg")

        if (errorDialog == null){
            errorDialog = ErrorDialog(this, msg, eventListener)
        } else {
            errorDialog!!.msg = msg
            errorDialog!!.eventListener = eventListener
        }

        errorDialog!!.show()
    }

    fun showLoading(){
        if (loadingDialog == null){
            loadingDialog = LoadingDialog(this)
        }
        loadingDialog!!.show()
    }

    fun hideLoading(){
        if (loadingDialog?.isShowing == true){
            loadingDialog?.cancel()
            loadingDialog = null
        }
    }

    fun toHome(){
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
        overridePendingTransition(android.R.anim.fade_in, 0)
    }
}
