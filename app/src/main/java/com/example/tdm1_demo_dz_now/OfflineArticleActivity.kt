package com.example.tdm1_demo_dz_now

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_offline_article.*
import java.util.*

class OfflineArticleActivity : AppCompatActivity() {
    //Text To Speech
    lateinit var mTTS: TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offline_article)
        if (intent!=null)
        {
            if(!intent.getStringExtra("titre").isEmpty())
            {
                news_title.text=intent.getStringExtra("titre")
            }
            if(!intent.getStringExtra("contenu").isEmpty())
            {
                news_des.text=intent.getStringExtra("contenu")

            }
            if(!intent.getStringExtra("sourceDate").isEmpty())
            {
                news_src.text=intent.getStringExtra("sourceDate")
            }
            else{
                news_src.text="21-09-2019"

            }
        }
        mTTS = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR){
                //if there is no error then set language
                mTTS.language = Locale.FRANCE
            }
        })
        button_tts.setOnClickListener {

            if(button_tts.isChecked) //get text from edit text
            {
                val toSpeak = news_title.text.toString()+news_des.text.toString()
                if (toSpeak == ""){
                    //if there is no text in edit text
                    Toast.makeText(this, "Lecture impossible", Toast.LENGTH_SHORT).show()
                }
                else{
                    //if there is text in edit text
                    Toast.makeText(this, toSpeak, Toast.LENGTH_SHORT).show()
                    mTTS.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null)
                }
            }

            else{
                if (mTTS.isSpeaking){
                    //if speaking then stop
                    mTTS.stop()
                    mTTS.shutdown()
                }
                else{
                    //if not speaking
                    Toast.makeText(this, "Not speaking", Toast.LENGTH_SHORT).show()
                }
            }


    }
} }
