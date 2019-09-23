package com.example.tdm1_demo_dz_now

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tdm1_demo_dz_now.Adapter.ListNewsAdapter
import com.example.tdm1_demo_dz_now.Common.Common
import com.example.tdm1_demo_dz_now.Data.ArticleRoomDatabase
import com.example.tdm1_demo_dz_now.Interface.NewsService
import com.example.tdm1_demo_dz_now.Model.News
import kotlinx.android.synthetic.main.activity_simple.*
import retrofit2.Call
import retrofit2.Response
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import kotlinx.android.synthetic.main.article_list_item.*


class SimpleActivity : AppCompatActivity() {

    private lateinit var mService:NewsService
    private lateinit var layoutManager: androidx.recyclerview.widget.LinearLayoutManager
    lateinit var adapter : ListNewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple)

        mService=Common.newsService
        mService.getNewsCategory(Common.getNewsAPI("business"))
       // mService.newsAct
            .enqueue(object : retrofit2.Callback<News> {
            override fun onFailure(call: Call<News>, t: Throwable) {
                Toast.makeText(baseContext,"Failed", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<News>, response: Response<News>) {

                Toast.makeText(baseContext,"success", Toast.LENGTH_SHORT).show()
                recycler_news.setHasFixedSize(true)
                layoutManager= androidx.recyclerview.widget.LinearLayoutManager(baseContext)
                recycler_news.layoutManager=layoutManager!!

                var adapter = ListNewsAdapter(response.body()!!.articles!!,baseContext)
                adapter.notifyDataSetChanged()
                recycler_news.adapter = adapter
            }

        })




    }





    fun delete(){
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                val db = ArticleRoomDatabase.getDatabase(this@SimpleActivity)
                val dao = db?.articleDao()

                // dao?.insert(article!!)
                var n =dao?.deleteAll()

                return null
            }

            override fun onPostExecute(result: Void?) {
                Toast.makeText(this@SimpleActivity, "Article Suvegardé", Toast.LENGTH_LONG).show()

            }
        }.execute()
    }
}
