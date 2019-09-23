package com.example.tdm1_demo_dz_now.Adapter

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tdm1_demo_dz_now.*
import com.example.tdm1_demo_dz_now.Data.ArticleEntity
import com.example.tdm1_demo_dz_now.Data.ArticleRoomDatabase
import com.example.tdm1_demo_dz_now.Interface.ItemClickListener
import com.example.tdm1_demo_dz_now.Model.Article
import com.example.tdm1_demo_dz_now.Model.Signet
import com.google.firebase.database.DatabaseReference
import com.squareup.picasso.Picasso

class ListNewsAdapter(private val articleList :List<Article>, private  val context: Context):
    androidx.recyclerview.widget.RecyclerView.Adapter<ListNewsViewHolder>() {
     //var db= Room.databaseBuilder(context,ApplicationDB::class.java,"ArticlesDB").build()
     // var db = ArticleRoomDatabase.getDatabase(context)
     // var dao = db.articleDao()
    // val articlRepository = ArticleRepository.getInstance(dao)

    private lateinit var database: DatabaseReference
    private lateinit var userId: String
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNewsViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val itemView=inflater.inflate(R.layout.article_list_item,parent,false)

        return ListNewsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ListNewsViewHolder, position: Int) {
       // Picasso.with(context).load(articleList[position].urlToImage)

        if (articleList[position].title!!.length>65) {
            holder.news_title.text = articleList[position].title!!.substring(0, 65) + "..."
        }else {
            holder.news_title.text = articleList[position].title!!
        }

        if (articleList[position].description!=null){

            if (articleList[position].description!!.length>65){
                holder.description.text = articleList[position].description!!.substring(0, 65) + "..."
            }
            else {
                holder.description.text = articleList[position].description!!
            }
        }
        if (articleList[position].publishedAt!=null){

                holder.source.text = articleList[position].publishedAt

        }

        Picasso.get().load(articleList[position].urlToImage).into(holder.a_news_image);

        holder.setItemClickListener(object: ItemClickListener
        {
            override fun onClick(view: View, position: Int) {
               val detail = Intent(context, DetailNewsActivity::class.java)
                detail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                detail.putExtra("webURL",articleList[position].url)
                context.startActivity(detail)
            }


            override fun onSaveLocal( position: Int) {
              //  Toast.makeText(context,articleList[position].title, Toast.LENGTH_SHORT).show()

                 Log.i("position",position.toString())
                 Log.i("title",articleList[position].title.toString())
                 Log.i("Content",articleList[position].content.toString())
                 Log.i("Description",articleList[position].description.toString())

                 val article = ArticleEntity(articleList[position].title?:"",articleList[position].content?:"", "ggg"
                     ,"ggggg","fefzgr",articleList[position].description?:"")


                 object : AsyncTask<Void, Void, Void>() {
                     override fun doInBackground(vararg voids: Void): Void? {
                         val db = ArticleRoomDatabase.getDatabase(context)
                         val dao = db?.articleDao()

                         dao?.insert(article)


                         return null
                     }

                     override fun onPostExecute(result: Void?) {
                         Toast.makeText(context, "Article Suvegardé", Toast.LENGTH_LONG).show()

                     }
                 }.execute()

                  //FireBase
                database=DataFirebase.getInstance()!!
                userId=DataFirebase.getUserId()!!
                //   database.child("articles").setValue(articleList[position].url)
                //  database.child("users").child(userId).child("articles").setValue(articleList[position].url)
                var signet = Signet( articleList[position].title!!,articleList[position].url!!)

                val userArticles = database.child("articles").child(userId).child("urls")
                val key = userArticles.push().key
                userArticles.child(key!!).setValue(signet)

/*                 val word = articleList[position].title.toString()
                 var article = ArticleEntity(1,articleList[position].title!!,articleList[position].content!!,
                     articleList[position].urlToImage!!,articleList[position].publishedAt!!,articleList[position].url!!)
                 if (TextUtils.isEmpty(word)) {
                     articlRepository.insert(article)
                 } else {
                     Toast.makeText(context, word + "inserted", Toast.LENGTH_SHORT).show()
                 }*/

                /*Thread{
                     var article = ArticleEntity()
                     article.title=articleList[position].title
                     article.content=articleList[position].content
                     article.description=articleList[position].description
                    // article.source=articleList[position].source

                    // db.articleDao().saveArticle(article)

                    db.articleDao().readArticles().forEach{
                        Log.i("kami",it.title)
                    }

                 }.start()*/
            }

            override fun sendSMS(view: View,position: Int) {
                val shareDataIntent=Intent(context, ShareViaSmsActivity::class.java)
                shareDataIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                shareDataIntent.putExtra("webURL",articleList[position]!!.url.toString())
                context.startActivity(shareDataIntent)

            }
            override fun sendEmail(view: View,position: Int) {
                val shareDataIntent=Intent(context, ShareViaMailActivity::class.java)
                shareDataIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                shareDataIntent.putExtra("subject",articleList[position]!!.title)
                shareDataIntent.putExtra("webURL",articleList[position]!!.url)
                context.startActivity(shareDataIntent)


            }



        })
    }
}