package com.sehan.stepforwater.di

import com.sehan.stepforwater.ui.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {

    viewModel { SplashViewModel(repository = get()) }
}