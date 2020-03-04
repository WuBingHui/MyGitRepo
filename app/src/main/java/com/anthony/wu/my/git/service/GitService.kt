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
    @GET("/repos/{userName}/{repo}/collaborators")
    fun getCollaborators(@Header("Authorization") authHeader: String,@Path("userName") userName:String ,@Path("repo") repo:String  ): Single<List<CollaboratorsDto>>

    /**
     * 登入
     */
    @GET("user")
    fun getUser(@Header("Authorization") authHeader: String): Single<UserInfoDto>


//
//    /**
//     * 送禮物
//     */
//    @FormUrlEncoded
//    @POST("api/room/gift/send")
//    fun sendGift(@FieldMap map: MutableMap<String?, String?>): Single<GeneralResponse>
//
//    /**
//     * 取得開寶箱清單list
//     * sessionIdAndToken 取得方式 => PrefHelper.getSession()
//     */
//    @GET("/api/user/activity")
//    fun getTreasureChest(@Header("Cookie") sessionIdAndToken:String): Single<TreasureChestDto>
//
//    /**
//     * 開寶箱
//     */
//    @FormUrlEncoded
//    @POST("/api/user/reward")
//    fun postOpenTreasureChest(@FieldMap Map: HashMap<String, String>): Single<OpenTreasureChestDto>
//
//    /**
//     * 直播室資訊
//     */
//    @GET("/live_room")
//    fun getLiveRoom(@Header("Cookie") sessionIdAndToken: String?, @Query("room_num") id: String?): Single<LiveRoom>
//
//    /**
//     * SocialInfo
//     * 直播室資訊的API欄位-call_social_api 等於 Ｙ的時候才打這支SocialInfo API
//     */
//    @GET("/api/social_info")
//    fun getSocialInfo(): Single<SocialInfo>
//
//    @POST("/api/user/watch_log")
//    fun postWatchLog(): Single<String>
//
//
//    /**
//     *  取得目前有在直播這場賽事的直播主
//     */
//    @GET("/room_other_schedule_rooms")
//    fun getOtherSchedule(@Header("Cookie") sessionIdAndToken: String?, @Query("room_id") id: String?): Single<OtherScheduleRooms>
//
//    /**
//     *  視頻清單
//     */
//    @GET("/api/video?limit=99")
//    fun getVideoListAll(@Query("start_at") time: String?, @Query("page") page: String?, @Query("account_id") account_id: String?): Single<VideoList>
//
//    /**
//     *  直播室 iframe，分流頻道
//     */
//    @GET("room/iframe/{id}")
//    fun getRoomIFrame(@Path("id") id: String?): Single<RoomIFrame>
//
//    /**
//     * 訂閱
//     */
//    @FormUrlEncoded
//    @POST("/api/user/focus")
//    fun postUserSubscription(@FieldMap Map: HashMap<String, String>): Single<Message>
//
//    /**
//     * 在聊天室內的會員資訊
//     */
//    @GET("/api/playerInfo")
//    fun getPlayerInfo(@Header("Cookie") sessionIdAndToken: String, @Query("id") id: String): Single<Player>
//
//    /**
//     * 頻道資訊
//     */
//    @GET("channel/info/{id}")
//     fun getChannelInfo(@Header("Cookie") sessionIdAndToken: String, @Path("id") channelNum: String): Single<ChannelOutPart>
//
//
//    /**
//     * 檢舉
//     */
//    @FormUrlEncoded
//    @POST("report")
//    fun postReport(@FieldMap Map: HashMap<String, String>): Single<Message>
//
//    /**
//     * 反饋
//     */
//    @FormUrlEncoded
//    @POST("feedback")
//    fun postFeedBack(@FieldMap Map: HashMap<String, String>): Single<Message>

}


