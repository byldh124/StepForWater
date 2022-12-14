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
            toast("???????????? 5?????? ?????? 16?????? ?????? ??????, ?????? ????????? ???????????????.")
            reset()
        } else if (!inputPw.matches(pwRegex)) {
            toast("??????????????? 8?????? ?????? ??????, ??????, ??????????????? ??????????????? ?????????.")
            reset()
        } else if (inputPw != binding.etPw2.text.toString()) {
            toast("???????????? ??????????????? ???????????? ????????????.")
            reset()
        } else if (!inputName.matches(nameRegex)) {
            toast("????????? 2?????? ?????? 8?????? ????????? ???????????????.")
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
                    showError("?????? ???????????? ????????? ?????????.", object : EventListener {
                        override fun onClick() {
                            reset()
                            binding.etId.requestFocus()
                        }
                    })
                }

                Constants.ResponseCode.FAIL -> {
                    showError("??????????????? ??????????????????.", object : EventListener {
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