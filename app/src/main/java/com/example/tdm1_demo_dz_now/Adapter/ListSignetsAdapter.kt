package com.example.tdm1_demo_dz_now.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.tdm1_demo_dz_now.DetailNewsActivity
import com.example.tdm1_demo_dz_now.Interface.ItemClickListener
import com.example.tdm1_demo_dz_now.Model.Article
import com.example.tdm1_demo_dz_now.Model.Signet
import com.example.tdm1_demo_dz_now.Model.WebSite
import com.example.tdm1_demo_dz_now.R

class ListSignetsAdapter(private  val context: Context,private val articles:List<Signet>):
    androidx.recyclerview.widget.RecyclerView.Adapter<ListSignetsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSignetsViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val itemView=inflater.inflate(R.layout.one_signet_layout,parent,false)
        return ListSignetsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: ListSignetsViewHolder, position: Int) {

        if (articles[position].title!!.length>65) {
            holder.title.text = articles[position].title!!.substring(0, 65) + "..."
        }else {
            holder.title.text = articles[position].title!!
        }

        if (articles[position].url!=null){

            if (articles[position].url!!.length>65){
                holder.url.text = articles[position].url!!.substring(0, 65) + "..."
            }
        }
        holder.setItemClickListener(object: ItemClickListener
        {
            override fun onClick(view: View, position: Int) {
                val detail = Intent(context, DetailNewsActivity::class.java)
                detail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                detail.putExtra("webURL",articles[position].url)

                context.startActivity(detail)     }

            override fun sendEmail(view: View, position: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun sendSMS(view: View, position: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}