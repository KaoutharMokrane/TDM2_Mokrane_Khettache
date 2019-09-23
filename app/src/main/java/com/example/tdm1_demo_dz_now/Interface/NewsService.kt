package com.example.tdm1_demo_dz_now.Interface
import com.example.tdm1_demo_dz_now.Model.News
import com.example.tdm1_demo_dz_now.Model.WebSite
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface NewsService {
    @get:GET("https://newsapi.org/v2/sources?apiKey=7ed4f80d1228431ba67cfc1f75855ce6")
    val sources: Call<WebSite>

    @get:GET("https://newsapi.org/v2/top-headlines?country=fr&apiKey=7ed4f80d1228431ba67cfc1f75855ce6")
    val news: Call<News>

    @GET
    fun getNewsCategory(@Url url:String) : Call <News>
}