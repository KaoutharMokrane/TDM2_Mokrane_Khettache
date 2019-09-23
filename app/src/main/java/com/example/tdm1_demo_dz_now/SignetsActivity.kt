package com.example.tdm1_demo_dz_now

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.tdm1_demo_dz_now.Adapter.ListNewsAdapter
import com.example.tdm1_demo_dz_now.Adapter.ListSavedAdapter
import com.example.tdm1_demo_dz_now.Adapter.ListSignetsAdapter
import com.example.tdm1_demo_dz_now.Data.ArticleEntity
import com.example.tdm1_demo_dz_now.Model.Signet
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_saved.*
import kotlinx.android.synthetic.main.activity_signets.*
import kotlinx.android.synthetic.main.activity_simple.*

class SignetsActivity : AppCompatActivity() {
    private lateinit var layoutManager: androidx.recyclerview.widget.LinearLayoutManager
    private var savedArticles : ArrayList<ArticleEntity> = ArrayList()
    private lateinit var database: DatabaseReference
    private lateinit var articlesReference :DatabaseReference
    private lateinit var userId: String
    private var articleListener: ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signets)
        setTitle(R.string.signets)

        database=DataFirebase.getInstance()!!
        userId=DataFirebase.getUserId()!!


        recycler_signets.setHasFixedSize(true)
        layoutManager= androidx.recyclerview.widget.LinearLayoutManager(baseContext)
        recycler_signets.layoutManager=layoutManager!!

        articlesReference = database.child("articles").child(userId).child("urls")
        val articleListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val articles: List<Signet> = dataSnapshot.children.mapNotNull { it.getValue<Signet>(Signet::class.java) }

                val adapter = ListSignetsAdapter(baseContext,articles)
                adapter.notifyDataSetChanged()
                recycler_signets.adapter = adapter
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("kami", "listener:onCancelled", databaseError.toException())
            }
        }
        articlesReference.addValueEventListener(articleListener)
        this.articleListener=articleListener
        /*recycler_saved.setHasFixedSize(true)
        layoutManager= androidx.recyclerview.widget.LinearLayoutManager(baseContext)
        recycler_saved.layoutManager=layoutManager

        var adapter = ListSavedAdapter(savedArticles,baseContext)
        adapter.notifyDataSetChanged()
        recycler_saved.adapter = adapter*/


    }


    /* override fun onStop() {
         super.onStop()
         // detach the event listener that watches changes in firebase database
         articleListener?.let {
             articleListener.
         }
     }*/


}
