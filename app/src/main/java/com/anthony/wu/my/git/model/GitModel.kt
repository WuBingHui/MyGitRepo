package com.anthony.wu.my.git.model

import com.anthony.wu.my.git.dto.request.AuthRequestBo
import com.anthony.wu.my.git.extension.ioToUi
import com.anthony.wu.my.git.service.GitService

class GitModel(val service: GitService) {


//
    fun getRespos(userName:String) = service.getRepos(userName).ioToUi()

    fun postLogin(authRequestBo: AuthRequestBo) = service.postLogin(authRequestBo).ioToUi()

}