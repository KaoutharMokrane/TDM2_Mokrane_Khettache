package com.example.tdm1_demo_dz_now.Adapter

import kotlinx.android.synthetic.main.news_layout.view.*
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.example.tdm1_demo_dz_now.Interface.ItemClickListener
import kotlinx.android.synthetic.main.news_layout.view.*
import kotlinx.android.synthetic.main.one_signet_layout.view.*
import kotlinx.android.synthetic.main.source_news_layout.view.*
import java.text.FieldPosition


class ListSignetsViewHolder (itemView:View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) ,View.OnClickListener {

    private lateinit var itemClickListener: ItemClickListener

        var title=itemView.textTitle
        var url=itemView.textUrl
        var news_title=itemView.news_title
        var description=itemView.news_des
        var source=itemView.news_src
        var button_save=itemView.button_save
        var a_news_image=itemView.a_news_image

        init {
            itemView.setOnClickListener(this)
        }
        fun setItemClickListener(itemClickListener: ItemClickListener){
            this.itemClickListener=itemClickListener
        }
        override fun onClick(v: View?){
            itemClickListener.onClick(v!!,adapterPosition)
        }
}