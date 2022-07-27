package com.sehan.stepforwater.utils

import android.util.Log

class SWLog {
    companion object {
        private const val TAG = "Custom Debug"

        fun d(any: Any, msg: String) {
            Log.d(TAG, "[${any.javaClass.simpleName.trim()}] , $msg")
        }

        fun e(any: Any, msg: String) {
            Log.e(TAG, "[${any.javaClass.simpleName.trim()}] , $msg")
        }

        fun i(msg: String) {
            Log.i(TAG, msg)
        }

        fun v(msg: String) {
            Log.v(TAG, msg)
        }

        fun d(msg: String) {
            Log.d(TAG, msg)
        }

        fun e(msg: String) {
            Log.e(TAG, msg)
        }

    }
}