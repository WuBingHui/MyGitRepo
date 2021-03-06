package com.anthony.wu.my.git.model

import com.anthony.wu.my.git.dto.request.AuthRequestBo
import com.anthony.wu.my.git.extension.ioToUi
import com.anthony.wu.my.git.service.GitService

class GitModel(val service: GitService) {


//
    fun getRespos(userName:String) = service.getRepos(userName).ioToUi()

    fun getUserList(userName:String) = service.getUserList(userName).ioToUi()

    fun getUser(authHeader: String) = service.getUser(authHeader).ioToUi()

    fun getCommits(userName:String,repo:String) = service.getCommits(userName,repo).ioToUi()

    fun getCollaborators(authHeader: String,userName:String,repo:String) = service.getCollaborators(authHeader,userName,repo).ioToUi()

}