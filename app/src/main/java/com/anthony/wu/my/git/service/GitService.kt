package com.anthony.wu.my.git.service


import com.anthony.wu.my.git.dto.response.*
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface GitService {

    /**
     * 取得倉庫清單
     */
    @GET("users/{userName}/repos")
    fun getRepos(@Path("userName") userName:String ): Single<List<ResposDto>>

    /**
     * 取得搜尋的用戶列表
     */
    @GET("search/users")
    fun getUserList(@Query("q") userName:String ): Single<UserDto>


    /**
     * 取得提交的訊息列表
     */
    @GET("repos/{userName}/{repo}/commits")
    fun getCommits(@Path("userName") userName:String ,@Path("repo") repo:String  ): Single<List<CommitsDto>>

    /**
     * 取得合作人列表
     */
    @GET("repos/{userName}/{repo}/collaborators")
    fun getCollaborators(@Header("Authorization") authHeader: String,@Path("userName") userName:String ,@Path("repo") repo:String ): Single<List<CollaboratorsDto>>

    /**
     * 登入
     */
    @GET("user")
    fun getUser(@Header("Authorization") authHeader: String): Single<UserInfoDto>
    

}


