package com.example.learnplay

import com.example.learnplay.dataClasses.TopicNetwork
import com.example.learnplay.dataClasses.TopicRequest
import com.example.learnplay.dataClasses.UserNetwork
import retrofit2.Call
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
}