package com.example.tdm1_demo_dz_now

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tdm1_demo_dz_now.Adapter.ListNewsAdapter
import com.example.tdm1_demo_dz_now.Adapter.ListSavedAdapter
import com.example.tdm1_demo_dz_now.Data.ArticleEntity
import com.example.tdm1_demo_dz_now.Data.ArticleRoomDatabase
import kotlinx.android.synthetic.main.activity_saved.*
import kotlinx.android.synthetic.main.activity_simple.*

class SavedActivity : AppCompatActivity() {


    private lateinit var layoutManager: androidx.recyclerview.widget.LinearLayoutManager
    private var savedArticles : ArrayList<ArticleEntity> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved)
        setTitle(R.string.sauvgardes)

        loadData()

        // var db = ArticleRoomDatabase.getDatabase(this)
        //var dao = db.articleDao()
        //val articlRepository = ArticleRepository.getInstance(dao)
        // text.text=articlRepository.allArticles[0].title


    }


    fun loadData() {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                val db = ArticleRoomDatabase.getDatabase(this@SavedActivity)
                val dao = db?.articleDao()
                  savedArticles.addAll(dao!!.getArticles())
                runOnUiThread {
                //    val recyclerView_bookmark = findViewById<RecyclerView>(R.id.r0ecyclerView_bookmark)
                  //
                    // recyclerView_bookmark.adapter = MainAdapter(savedArticles, this@BookmarkActivity)

                    recycler_saved.setHasFixedSize(true)
                    layoutManager= androidx.recyclerview.widget.LinearLayoutManager(baseContext)
                    recycler_saved.layoutManager=layoutManager

                    var adapter = ListSavedAdapter(savedArticles,baseContext)
                    adapter.notifyDataSetChanged()
                    recycler_saved.adapter = adapter


                }


                return null
            }

            override fun onPostExecute(result: Void?) {
                Toast.makeText(applicationContext, "Vos Articles sauvegardés", Toast.LENGTH_LONG).show()

            }
        }.execute()
    }

}
