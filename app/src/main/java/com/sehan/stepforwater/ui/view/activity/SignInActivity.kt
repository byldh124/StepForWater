package com.sehan.stepforwater.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.gson.JsonObject
import com.sehan.stepforwater.R
import com.sehan.stepforwater.application.App
import com.sehan.stepforwater.base.BaseActivity
import com.sehan.stepforwater.databinding.ActivitySignInBinding
import com.sehan.stepforwater.ui.view.dialog.ErrorDialog
import com.sehan.stepforwater.ui.viewmodel.SignInViewModel
import com.sehan.stepforwater.utils.Constants
import com.sehan.stepforwater.utils.SWLog
import com.sehan.stepforwater.utils.SWUtils
import com.sehan.stepforwater.utils.view.log
import com.sehan.stepforwater.utils.view.toast
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInActivity : BaseActivity() {
    lateinit var binding: ActivitySignInBinding

    lateinit var inputId: String
    lateinit var inputPw: String

    private val viewModel: SignInViewModel by viewModel()

    private val idRegex = Regex("^[a-zA-Z0-9]{5,16}$")
    private val pwRegex =
        Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@!%*#?&])[A-Za-z\\d$@!%*#?&]{8,}\$")

    private var doSignIn = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        binding.activity = this
    }

    private fun signIn(hashPw: String){

        log("signIn() api call")

        viewModel.signIn(inputId, hashPw)

        viewModel.user.observe(this) {
            App.id = it.id
            App.name = it.name
            App.profile = it.profile
            App.information = it.information
            App.settings = it.settings

            hideLoading()
            toast("로그인 성공")

            toHome()
        }

        viewModel.userStatus.observe(this) {
            when (it){
                Constants.ResponseCode.NOT_EXIST -> {
                    showError("아이디가 존재하지 않습니다.", object : ErrorDialog.EventListener{
                        override fun onClick() {
                            binding.etId.requestFocus()
                            reset()
                        }
                    })
                }

                Constants.ResponseCode.WRONG_PASSWORD -> {
                    showError("잘못된 비밀번호 입니다.", object : ErrorDialog.EventListener{
                        override fun onClick() {
                            binding.etPw.requestFocus()
                            reset()
                        }
                    })
                }

                else -> {
                    showError("네트워크가 원할하지 않습니다.", object : ErrorDialog.EventListener{
                        override fun onClick() {
                            reset()
                        }
                    })
                }
            }
        }
    }

    private fun getSalt() {

        viewModel.getSalt(inputId)

        log("getSalt() api call")

        viewModel.salt.observe(this) {
            val hashPw = SWUtils.hashingKey(inputPw, it)
            signIn(hashPw)
        }

        viewModel.saltStatus.observe(this) {
            when(it){
                Constants.ResponseCode.NOT_EXIST -> {
                    showError("아이디가 존재하지 않습니다.", object : ErrorDialog.EventListener{
                        override fun onClick() {
                            binding.etId.requestFocus()
                            reset()
                        }
                    })
                }

                else -> {
                    showError("네트워크가 원할하지 않습니다.", object : ErrorDialog.EventListener{
                        override fun onClick() {
                            reset()
                        }
                    })
                }
            }
        }

    }

    fun checkField(vw: View) {
        if (doSignIn) return
        doSignIn = true
        showLoading()

        inputId = binding.etId.text.toString()
        inputPw = binding.etPw.text.toString()

        if (!inputId.matches(idRegex)) {
            toast("아이디는 5글자 이상 16글자 이하 영문, 숫자로 이루어져 있습니다.")
            reset()
        } else if (!inputPw.matches(pwRegex)) {
            toast("비밀번호는 8글자 이상 영문, 숫자, 특수기호가 포함되어야 합니다.")
            reset()
        } else {
            getSalt()
        }
    }

    private fun reset(){
        doSignIn = false
        hideLoading()
    }

    fun goToSignUpActivity(vw: View) {
        startActivity(Intent(this, SignUpActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, 0)
    }
}