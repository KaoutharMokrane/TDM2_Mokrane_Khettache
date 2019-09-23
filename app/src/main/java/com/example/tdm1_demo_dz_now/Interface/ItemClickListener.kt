package com.example.tdm1_demo_dz_now.Interface

import android.view.View
import com.example.tdm1_demo_dz_now.AppExecutors

interface ItemClickListener {
    fun onClick(view: View, position:Int){
    }

    fun onSaveLocal(position:Int){
    }

    fun onSaveExternal(position: Int){

    }
   fun sendSMS(view: View,position: Int)
   fun sendEmail(view: View,position: Int)
   // fun removeItem(position: Int)

}