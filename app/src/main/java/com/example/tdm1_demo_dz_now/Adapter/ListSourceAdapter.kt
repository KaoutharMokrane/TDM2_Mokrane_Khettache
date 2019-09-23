package com.example.tdm1_demo_dz_now.Adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tdm1_demo_dz_now.AppExecutors
import com.example.tdm1_demo_dz_now.Interface.ItemClickListener
import com.example.tdm1_demo_dz_now.Model.WebSite
import com.example.tdm1_demo_dz_now.R

class ListSourceAdapter(private  val context: Context,private val webSite:WebSite):
    androidx.recyclerview.widget.RecyclerView.Adapter<ListSourceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSourceViewHolder {
         val inflater= LayoutInflater.from(parent.context)
         val itemView=inflater.inflate(R.layout.source_news_layout,parent,false)
         return ListSourceViewHolder(itemView)
    }

    override fun getItemCount(): Int {
         return webSite.sources!!.size
    }

    override fun onBindViewHolder(holder: ListSourceViewHolder, position: Int) {
        holder.source_title.text=webSite.sources!![position].name
        holder.setItemClickListener(object: ItemClickListener
        {

            override fun onClick(view: View, position: Int) {

                Toast.makeText(context,"Will be implement",Toast.LENGTH_SHORT).show()
            }

            override fun sendEmail(view: View, position: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun sendSMS(view: View, position: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }


        })
    }
    }