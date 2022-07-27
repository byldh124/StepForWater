package com.sehan.stepforwater.di

import com.sehan.stepforwater.ui.viewmodel.SignInViewModel
import com.sehan.stepforwater.ui.viewmodel.SignUpViewModel
import com.sehan.stepforwater.ui.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {

    viewModel { SplashViewModel(repository = get()) }

    viewModel { SignUpViewModel(repository = get()) }

    viewModel { SignInViewModel(repository = get()) }
}