package com.anthony.wu.my.git


import android.content.Context
import androidx.multidex.MultiDexApplication
import com.anthony.wu.my.git.koin.gitModule
import com.anthony.wu.my.git.koin.repositoryModule
import com.anthony.wu.my.git.koin.serviceModule
import com.anthony.wu.my.git.koin.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class GitApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        context = this@GitApplication

        startKoin {
            androidContext(this@GitApplication)
            modules(listOf(viewModelModule,gitModule, serviceModule,repositoryModule))
        }


    }

    companion object {
        private var context: Context? = null
        fun getContext(): Context? = context
    }




}



