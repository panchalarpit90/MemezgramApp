package com.example.memezgram

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


const val BASE_URL = "https://meme-api.com/"
interface MemesInterface {
    @GET("gimme/dankmemes/{count}")
    fun getMemes(@Path("count") count: Int): Call<MemeModel>
}

object MemesService{
    val MemesInstance:MemesInterface
    init{
        val retrofit=Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()
        MemesInstance=retrofit.create(MemesInterface::class.java)
    }
}
