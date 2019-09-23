package com.example.tdm1_demo_dz_now.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tdm1_demo_dz_now.AppExecutors
import com.example.tdm1_demo_dz_now.Data.ArticleEntity
import com.example.tdm1_demo_dz_now.DetailNewsActivity
import com.example.tdm1_demo_dz_now.Interface.ItemClickListener
import com.example.tdm1_demo_dz_now.OfflineArticleActivity
import com.example.tdm1_demo_dz_now.R

class ListSavedAdapter(private val articleList :List<ArticleEntity>, private  val context: Context):
    androidx.recyclerview.widget.RecyclerView.Adapter<ListSavedViewHolder>() {
    //var db= Room.databaseBuilder(context,ApplicationDB::class.java,"ArticlesDB").build()

    // var db = ArticleRoomDatabase.getDatabase(context)
    // var dao = db.articleDao()
    // val articlRepository = ArticleRepository.getInstance(dao)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSavedViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val itemView=inflater.inflate(R.layout.saved_layout,parent,false)
        return ListSavedViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ListSavedViewHolder, position: Int) {
        // Picasso.with(context).load(articleList[position].urlToImage)

        if (articleList[position].title.length>65) {
            holder.news_title.text = articleList[position].title!!.substring(0, 65) + "..."
        }else {
            holder.news_title.text = articleList[position].title
        }

        if (articleList[position].description!=null){

            if (articleList[position].description.length>65){
                holder.description.text = articleList[position].description.substring(0, 65) + "..."
            }
        }
  // if(articleList[position].date!=null)
        {
            holder.source.text=articleList[position].date.toString()
        }


//    Picasso.get().load(articleList[position].urlToImage).into(holder.a_news_image);

        holder.setItemClickListener(object: ItemClickListener
        {
            override fun onClick(view: View, position: Int) {
                val detail = Intent(context, OfflineArticleActivity::class.java)
                detail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                detail.putExtra("titre",articleList[position].title)
                detail.putExtra("contenu",articleList[position].content)
                detail.putExtra("sourceDate",articleList[position].date)

              //  detail.putExtra("webURL",articleList[position].url)

                context.startActivity(detail)
            }

            override fun sendSMS(view: View, position: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun sendEmail(view: View, position: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }


    })

    }
}