package com.example.tdm1_demo_dz_now.Adapter

import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.example.tdm1_demo_dz_now.Interface.ItemClickListener
import com.example.tdm1_demo_dz_now.PICK_CONTACT_REQUEST

import kotlinx.android.synthetic.main.article_list_item.view.*




import kotlinx.android.synthetic.main.source_news_layout.view.*
import java.text.FieldPosition

class ListNewsViewHolder (itemView:View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) ,View.OnClickListener {

    private lateinit var itemClickListener: ItemClickListener
    var news_title=itemView.news_title
    var description=itemView.news_des
    var source=itemView.news_src
    var button_save=itemView.button_save
    var a_news_image=itemView.a_news_image
    var button_signets=itemView.button_signets
    var button_share_sms=itemView.button_share_sms
    var button_share_mail=itemView.button_share_mail
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

   button_share_sms.setOnClickListener(){
       itemClickListener.sendSMS(itemView!!,adapterPosition)
   }

        button_share_mail.setOnClickListener(){
            itemClickListener.sendEmail(itemView!!,adapterPosition)
        }
        /**button_signets.setOnClickListener{
            this.itemClickListener.onSaveExternal(adapterPosition)
        }**/
    }



    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener=itemClickListener
    }
    override fun onClick(v:View?){
        itemClickListener.onClick(v!!,adapterPosition)
    }






}