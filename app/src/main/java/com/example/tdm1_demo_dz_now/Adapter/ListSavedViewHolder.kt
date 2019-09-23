package com.example.tdm1_demo_dz_now.Adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.example.tdm1_demo_dz_now.Interface.ItemClickListener


import kotlinx.android.synthetic.main.saved_layout.view.*
import kotlinx.android.synthetic.main.saved_layout.view.button_save
import kotlinx.android.synthetic.main.saved_layout.view.news_des
import kotlinx.android.synthetic.main.saved_layout.view.news_title

import java.text.FieldPosition

class ListSavedViewHolder (itemView:View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) ,View.OnClickListener {

    private lateinit var itemClickListener: ItemClickListener
    var news_title=itemView.news_title
    var description=itemView.news_des
    var source=itemView.news_src
    var button_save=itemView.button_save

    init {


            itemView.setOnClickListener(this)

            button_save.setOnClickListener{

                if(button_save.isChecked){

                    this.itemClickListener.onSaveLocal(adapterPosition)
                    this.itemClickListener.onSaveExternal(adapterPosition)
                }
                else{
                    //unchecked: we need to delete from Room and Firebase


                }
            }


        }
    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener=itemClickListener
    }
    override fun onClick(v:View?){
        itemClickListener.onClick(v!!,adapterPosition)
    }


}