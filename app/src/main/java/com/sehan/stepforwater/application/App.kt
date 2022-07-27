package com.sehan.stepforwater.application

import android.app.Application
import android.security.identity.AccessControlProfile
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import com.sehan.stepforwater.di.appModules
import com.sehan.stepforwater.di.fragmentModules
import com.sehan.stepforwater.di.viewModelModules
import com.sehan.stepforwater.model.PersonalInfo
import com.sehan.stepforwater.utils.SWPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    companion object{
        lateinit var prefs: SWPrefs

        var id :String = ""
        var name: String = ""
        var information: String = ""
        var profile: String = ""
        var settings: String = ""
    }

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)

        startKoin{
            androidContext(this@App)
            fragmentFactory()
            modules(appModules)
            modules(fragmentModules)
            modules(viewModelModules)
        }

        prefs = SWPrefs(applicationContext)

    }
}