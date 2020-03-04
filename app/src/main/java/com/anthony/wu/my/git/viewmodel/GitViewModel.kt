package com.anthony.wu.my.git.viewmodel

import androidx.lifecycle.MutableLiveData
import com.anthony.wu.my.git.base.BaseViewModel
import com.anthony.wu.my.git.dto.Resource
import com.anthony.wu.my.git.dto.response.*
import com.anthony.wu.my.git.extension.addTo
import com.anthony.wu.my.git.extension.ioToUi
import com.anthony.wu.my.git.model.GitModel
import com.anthony.wu.my.git.utils.SharedPreferencesUtils
import org.koin.core.KoinComponent
import org.koin.core.inject

class GitViewModel( private val gitModel: GitModel) : BaseViewModel() {


    val onRespos: MutableLiveData<Resource<List<ResposDto>>> by lazy { MutableLiveData<Resource<List<ResposDto>>>() }

    val onLogin: MutableLiveData<Resource<UserInfoDto>> by lazy { MutableLiveData<Resource<UserInfoDto>>() }

    val onUserList: MutableLiveData<Resource<UserDto>> by lazy { MutableLiveData<Resource<UserDto>>() }

    val onCommits: MutableLiveData<Resource<List<CommitsDto>>> by lazy { MutableLiveData<Resource<List<CommitsDto>>>() }

    val onCollaborators: MutableLiveData<Resource<List<CollaboratorsDto>>> by lazy { MutableLiveData<Resource<List<CollaboratorsDto>>>() }

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

                onLogin.value = Resource.success(dto,authHeader)

            }, { t: Throwable? -> onLogin.value = Resource.error(t?.message, null) }

        ).addTo(compositeDisposable)

    }

    fun getCommits(userName:String,repo:String) {

        gitModel.getCommits(userName,repo).ioToUi().subscribe(
            { dto ->

                onCommits.value = Resource.success(dto)

            }, { t: Throwable? -> onCommits.value = Resource.error(t?.message, null) }

        ).addTo(compositeDisposable)

    }

    fun getCollaborators(authHeader: String,userName:String,repo:String) {

        gitModel.getCollaborators(authHeader,userName,repo).ioToUi().subscribe(
            { dto ->

                onCollaborators.value = Resource.success(dto)

            }, { t: Throwable? -> onCollaborators.value = Resource.error(t?.message, null) }

        ).addTo(compositeDisposable)

    }

}
