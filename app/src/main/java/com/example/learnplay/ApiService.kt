package com.example.learnplay

import com.example.learnplay.dataClasses.NotificationObject
import com.example.learnplay.dataClasses.Quest
import com.example.learnplay.dataClasses.TaskResponse
import com.example.learnplay.dataClasses.TopicNetwork
import com.example.learnplay.dataClasses.TopicRequest
import com.example.learnplay.dataClasses.UserNetwork
import com.example.learnplay.dataClasses.UserRating
import com.example.learnplay.fragments.QuestRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*



interface ApiService {
    @POST("entry")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<Void>

    @GET("profile/info")
    fun fetchData(): Call<UserNetwork>

    @PUT("profile/changeName/{newName}")
    fun changeName(@Path("newName") newName: String): Call<Void>

    @GET("education/getListProgress")
    fun getTopics(): Call<List<TopicNetwork>>

    @POST("quest/startTopicQuest")
    fun startTopicQuest(@Body request: TopicRequest): Call<Void>

    @GET("quest/getNewTask")
    fun getTask(): Call<TaskResponse>

    @PUT("quest/successTask/{taskId}")
    fun successTask(@Path("taskId") taskId: Int): Call<Boolean>

    @PUT("quest/failedTask/{taskId}")
    fun failedTask(@Path("taskId") taskId: Int): Call<Boolean>
    @GET("quest/isEndQuest")
    fun isEndQuest(): Call<Boolean>

    @POST("quest/startUserQuest")
    fun startUserQuest(@Body request: QuestRequest): Call<Void>

    @GET("quest/successQuest")
    fun successQuest(): Call<Boolean>

    @POST("quest/startDailyQuest")
    fun startDailyQuest(): Call<Quest>

    @POST("quest/startRandomQuest")
    fun startRandomQuest(): Call<Quest>

    @GET("rank/getRankList")
     fun getUsers(): Call<List<UserRating>>

     @GET("rank/getRank")
     fun getUser(): Call<UserRating>

    @PUT("setting/changeEmail/{newEmail}")
    fun changeEmail(@Path("newEmail") newEmail: String): Call<Boolean>

    @PUT("setting/changePassword/{oldPassword}/{newPassword}")
    fun changePassword(
        @Path("oldPassword") oldPassword: String,
        @Path("newPassword") newPassword: String
    ): Call<Boolean>

    @GET("/getListNotification")
    fun getListNotification(): Call<List<NotificationObject>>

    @PUT("/viewedNotification/{note_obj_id}")
    fun viewedNotification(@Path("note_obj_id") noteObjId: Long): Call<Void>


}