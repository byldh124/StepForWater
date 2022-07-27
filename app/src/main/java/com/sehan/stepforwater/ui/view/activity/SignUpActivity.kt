package com.sehan.stepforwater.ui.view.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.google.firebase.messaging.FirebaseMessaging
import com.sehan.stepforwater.R
import com.sehan.stepforwater.application.App
import com.sehan.stepforwater.base.BaseActivity
import com.sehan.stepforwater.databinding.ActivitySignUpBinding
import com.sehan.stepforwater.model.PersonalInfo
import com.sehan.stepforwater.ui.view.dialog.ErrorDialog.EventListener
import com.sehan.stepforwater.ui.viewmodel.SignUpViewModel
import com.sehan.stepforwater.utils.Constants
import com.sehan.stepforwater.utils.SWLog
import com.sehan.stepforwater.utils.SWUtils
import com.sehan.stepforwater.utils.view.log
import com.sehan.stepforwater.utils.view.toast
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.security.SecureRandom


class SignUpActivity : BaseActivity() {
    lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModel()

    var inputId = ""
    var inputPw = ""
    var inputName = ""

    var isDoingSignUp: Boolean = false

    private val idRegex = Regex("^[a-zA-Z0-9]{5,16}$")
    private val pwRegex =
        Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@!%*#?&])[A-Za-z\\d$@!%*#?&]{8,}\$")
    private val nameRegex = Regex("^(.{2,8})$")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        binding.activity = this

        initView()
    }

    private fun initView(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowTitleEnabled(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun requestSignUp() {
        if (!isDoingSignUp) {
            showLoading()
            isDoingSignUp = true
            checkField()
        }
    }

    private fun checkField() {

        log("checkField() call")

        inputId = binding.etId.text.toString()
        inputPw = binding.etPw.text.toString()
        inputName = binding.etName.text.toString()

        if (!inputId.matches(idRegex)) {
            toast("아이디는 5글자 이상 16글자 이하 영문, 숫자 사용이 가능합니다.")
            reset()
        } else if (!inputPw.matches(pwRegex)) {
            toast("비밀번호는 8글자 이상 영문, 숫자, 특수기호가 포함되어야 합니다.")
            reset()
        } else if (inputPw != binding.etPw2.text.toString()) {
            toast("입력하실 비밀번호가 일치하지 않습니다.")
            reset()
        } else if (!inputName.matches(nameRegex)) {
            toast("이름은 2글자 이상 8글자 이하만 가능합니다.")
            reset()
        } else {
            encryptPw()
        }
    }

    private fun encryptPw() {
        val salt = getSalt()

        val hashKey = SWUtils.hashingKey(inputPw, salt)

        signUp(hashKey, salt)
    }

    private fun signUp(hashPw: String, salt: String) {
        viewModel.signUp(inputId, hashPw, inputName, salt)

        log( "signUp() api call")

        viewModel.signUpCode.observe(this) {
            log("signUpCode-observe() code : $it")
            when (it) {
                Constants.ResponseCode.SUCCESS -> {
                    App.id = inputId
                    App.name = inputName
                    registerToken()
                }

                Constants.ResponseCode.ALREADY_EXIT -> {
                    showError("이미 존재하는 아이디 입니다.", object : EventListener {
                        override fun onClick() {
                            reset()
                            binding.etId.requestFocus()
                        }
                    })
                }

                Constants.ResponseCode.FAIL -> {
                    showError("회원가입이 실패했습니다.", object : EventListener {
                        override fun onClick() {
                            reset()
                        }
                    })
                }
            }
        }
    }

    private fun registerToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful) {
                val token = it.result
                log("registerToken() token = $token")
                viewModel.registerToken(App.id, token)

                viewModel.tokenCode.observe(this) {
                    toHome()
                }
            } else {
                toHome()
            }
            reset()
        }
    }

    fun reset() {
        isDoingSignUp = false
        hideLoading()
    }

    private fun getSalt(): String {
        val rnd = SecureRandom()
        val temp = ByteArray(16)
        rnd.nextBytes(temp)

        return SWUtils.byteToString(temp)
    }
}