package com.anthony.wu.my.git.viewmodel

import androidx.lifecycle.MutableLiveData
import com.anthony.wu.my.git.base.BaseViewModel
import com.anthony.wu.my.git.dto.Resource
import com.anthony.wu.my.git.dto.response.ResposDto
import com.anthony.wu.my.git.dto.response.UserDto
import com.anthony.wu.my.git.dto.response.UserInfoDto
import com.anthony.wu.my.git.extension.addTo
import com.anthony.wu.my.git.extension.ioToUi
import com.anthony.wu.my.git.model.GitModel
import org.koin.core.KoinComponent
import org.koin.core.inject

class GitViewModel( private val gitModel: GitModel) : BaseViewModel() {

    val onRespos: MutableLiveData<Resource<List<ResposDto>>> by lazy { MutableLiveData<Resource<List<ResposDto>>>() }

    val onLogin: MutableLiveData<Resource<UserInfoDto>> by lazy { MutableLiveData<Resource<UserInfoDto>>() }

    val onUserList: MutableLiveData<Resource<UserDto>> by lazy { MutableLiveData<Resource<UserDto>>() }


    fun getRespos(userName:String) {

        gitModel.getRespos(userName).ioToUi().subscribe(
            { dto ->

                    onRespos.value = Resource.success(dto)

            }, { t: Throwable? -> onRespos.value = Resource.error(t?.message, null) }

        ).addTo(compositeDisposable)

    }

    fun getUserList(nameName:String) {

        gitModel.getUserList(nameName).ioToUi().subscribe(
            { dto ->

                onUserList.value = Resource.success(dto)

            }, { t: Throwable? -> onUserList.value = Resource.error(t?.message, null) }

        ).addTo(compositeDisposable)

    }

    fun getUser(authHeader: String) {

        gitModel.getUser(authHeader).ioToUi().subscribe(
            { dto ->

                onLogin.value = Resource.success(dto)

            }, { t: Throwable? -> onLogin.value = Resource.error(t?.message, null) }

        ).addTo(compositeDisposable)

    }

}
