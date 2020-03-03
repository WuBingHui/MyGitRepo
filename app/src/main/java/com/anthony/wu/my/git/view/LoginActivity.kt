package com.anthony.wu.my.git.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anthony.wu.my.git.GitApplication
import com.anthony.wu.my.git.R
import com.anthony.wu.my.git.base.BaseActivity
import com.anthony.wu.my.git.dto.request.AuthRequestBo
import com.anthony.wu.my.git.koin.gitModule
import com.anthony.wu.my.git.koin.repositoryModule
import com.anthony.wu.my.git.koin.serviceModule
import com.anthony.wu.my.git.koin.viewModelModule
import com.anthony.wu.my.git.viewmodel.GitViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin

class LoginActivity : BaseActivity() {

    private val viewModel by viewModel<GitViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



//        viewModel.getRespos("WuBingHui")

//        val authRequestBo = AuthRequestBo()
//
//        viewModel.postLogin(authRequestBo)

        initView()

    }

    private fun initView(){

        loginBtn.setOnClickListener {

        }

        skip.setOnClickListener {

            val intent = Intent()
            intent.setClass(this, PublicRepositoriesActivity::class.java)
            startActivity(intent)

        }

    }
}
