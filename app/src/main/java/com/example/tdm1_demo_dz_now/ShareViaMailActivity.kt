package com.example.tdm1_demo_dz_now

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_share_via_mail.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class ShareViaMailActivity : AppCompatActivity() {
    lateinit var appExecutors: AppExecutors
    lateinit var message:String
    lateinit var title:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_via_mail)
        appExecutors = AppExecutors()
        if (intent!=null)
            if(!intent.getStringExtra("webURL").isEmpty())
                message="J'ai aimé cet article, jettez un coup d'oeil:" + intent.getStringExtra("webURL")
        if(!intent.getStringExtra("subject").isEmpty())
            title= "[Partage d'acticle]["+intent.getStringExtra("subject")+"]"

        mailObject.setText("[Partage d'acticle]")
        mailBody.setText(message)

        btn_send_mail.setOnClickListener {
            sendEmail()
            Toast.makeText(
                applicationContext,
                "Mail envoyé", Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun sendEmail(){
        appExecutors.diskIO().execute {
            val props = System.getProperties()
            props.put("mail.smtp.host", "smtp.gmail.com")
            props.put("mail.smtp.socketFactory.port", "465")
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
            props.put("mail.smtp.auth", "true")
            props.put("mail.smtp.port", "465")

            val session =  Session.getInstance(props,
                object : javax.mail.Authenticator() {
                    //Authenticating the password
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(Credentials.EMAIL, Credentials.PASSWORD)
                    }
                })

            try {
                //Creating MimeMessage object
                val mm = MimeMessage(session)
                val emailId = sendTo.text.toString()
                if(emailId!=null) {
                    //Setting sender address
                    mm.setFrom(InternetAddress(Credentials.EMAIL))
                    //Adding receiver
                    mm.addRecipient(
                        Message.RecipientType.TO,
                        InternetAddress(emailId)
                    )
                    //Adding subject
                    mm.subject = mailObject.text.toString()
                    //Adding message
                    mm.setText(mailBody.text.toString())

                    //Sending email
                    Transport.send(mm)
                    appExecutors.mainThread().execute {
                        //Something that should be executed on main thread.

                    }


                }

            } catch (e: MessagingException) {
                e.printStackTrace()
            }
        }
    }}

