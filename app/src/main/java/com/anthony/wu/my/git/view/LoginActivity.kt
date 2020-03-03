package com.anthony.wu.my.git.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.lifecycle.Observer
import com.anthony.wu.my.git.GitApplication
import com.anthony.wu.my.git.R
import com.anthony.wu.my.git.base.BaseActivity
import com.anthony.wu.my.git.dto.Status
import com.anthony.wu.my.git.dto.request.AuthRequestBo
import com.anthony.wu.my.git.koin.gitModule
import com.anthony.wu.my.git.koin.repositoryModule
import com.anthony.wu.my.git.koin.serviceModule
import com.anthony.wu.my.git.koin.viewModelModule
import com.anthony.wu.my.git.viewmodel.GitViewModel
import com.anthony.wu.my.git.widget.CustomLoadingDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_public_repositories.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin

class LoginActivity : BaseActivity() {

    private val viewModel by viewModel<GitViewModel>()

    private var customLoadingDialog: CustomLoadingDialog? = null

    companion object{
        private const val USER_NAME = "userName"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



//        viewModel.getRespos("WuBingHui")

//        val authRequestBo = AuthRequestBo()
//
//        viewModel.postLogin(authRequestBo)

        initView()

        initViewModel()

    }

    private fun initView(){

        customLoadingDialog = CustomLoadingDialog.newInstance()

        loginBtn.setOnClickListener {
            login()
        }

        skip.setOnClickListener {

            val intent = Intent()
            intent.setClass(this, SearchActivity::class.java)
            startActivity(intent)

        }

    }

    private fun initViewModel(){

        viewModel.onLogin.observe(this, Observer {
                dto ->
            when (dto.status) {
                Status.SUCCESS -> {

                    dto.data?.let {

                        val intent = Intent()
                        intent.putExtra(USER_NAME,it.login)
                        intent.setClass(this, PublicRepositoriesActivity::class.java)
                        startActivity(intent)

                    }

                }
                Status.ERROR -> {
                    Toast.makeText(this, dto.message, Toast.LENGTH_SHORT).show()
                }
            }

            customLoadingDialog?.dismissAllowingStateLoss()

        })



    }

    private fun login(){

        val userName = accountEditText.text.toString()
        val password = passwordEditText.text.toString()

        val base ="$userName:$password"

        val authHeader = "Basic ${Base64.encodeToString(base.toByteArray(),Base64.NO_WRAP)}"

        customLoadingDialog?.show(supportFragmentManager, customLoadingDialog!!.tag)

        viewModel.getUser(authHeader)

    }


}
