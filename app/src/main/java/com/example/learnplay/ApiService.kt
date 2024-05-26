package com.example.learnplay

import com.example.learnplay.dataClasses.TaskResponse
import com.example.learnplay.dataClasses.TopicNetwork
import com.example.learnplay.dataClasses.TopicRequest
import com.example.learnplay.dataClasses.UserNetwork
import com.example.learnplay.dataClasses.UserRating
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

    @GET("quest/successQuest")
    fun successQuest(): Call<Boolean>

    @GET("rank/getRankList")
     fun getUsers(): Call<List<UserRating>>

     @GET("rank/getRank")
     fun getUser(): Call<UserRating>
}