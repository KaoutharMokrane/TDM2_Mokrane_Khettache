package com.example.tdm1_demo_dz_now

import android.telephony.SmsManager
import android.widget.Toast
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


fun sendEmail(appExecutors:AppExecutors,from:String,to:String,subject:String,body:String){
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
            val emailId = to
            //Setting sender address
            mm.setFrom(InternetAddress(Credentials.EMAIL))
            //Adding receiver
            mm.addRecipient(
                Message.RecipientType.TO,
                InternetAddress(emailId)
            )
            //Adding subject
            mm.subject = subject
            //Adding message
            mm.setText(body)

            //Sending email
            Transport.send(mm)

            appExecutors.mainThread().execute {
                //Something that should be executed on main thread.
            }

        } catch (e: MessagingException) {
            e.printStackTrace()
        }
    }
}

// ---sends an SMS message to another device---
fun sendSMS(phoneNumber: String, message: String) {

    val smsManager = SmsManager.getDefault()
    smsManager.sendTextMessage(
        phoneNumber,
        null,
        message,
        null,
        null)


}