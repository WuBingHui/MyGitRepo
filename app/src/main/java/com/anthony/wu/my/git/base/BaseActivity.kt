package com.anthony.wu.my.git.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.OrientationEventListener
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.anthony.wu.my.git.R
import com.anthony.wu.my.git.koin.gitModule
import com.anthony.wu.my.git.koin.repositoryModule
import com.anthony.wu.my.git.koin.serviceModule
import com.anthony.wu.my.git.koin.viewModelModule
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.SingleSubject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    protected var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags = window.decorView.systemUiVisibility
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.decorView.systemUiVisibility = flags
            window.statusBarColor = ContextCompat.getColor(this, R.color.md_blue_400)
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }



}