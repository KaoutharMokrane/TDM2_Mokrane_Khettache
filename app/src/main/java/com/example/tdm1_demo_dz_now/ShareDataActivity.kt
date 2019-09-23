package com.example.tdm1_demo_dz_now

import android.app.Activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.*
import android.provider.ContactsContract.CommonDataKinds.*
import android.provider.ContactsContract.CommonDataKinds.Phone.*

import android.telephony.SmsManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.commit451.mailgun.Attachment
import com.commit451.mailgun.Contact
import com.commit451.mailgun.Mailgun
import com.commit451.mailgun.SendMessageRequest
import kotlinx.android.synthetic.main.activity_share_data.*
import okhttp3.MediaType
import okhttp3.RequestBody
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class ShareDataActivity : AppCompatActivity() {
    lateinit var mailgun:Mailgun
    lateinit var requestBuilder:SendMessageRequest.Builder
    lateinit var appExecutors: AppExecutors
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_data)
        appExecutors = AppExecutors()
        if (intent!=null)
            if(!intent.getStringExtra("webURL").isEmpty()) {


                                                 }
        shareTxtBtnDefault.setOnClickListener {
            val s = textToShare.text.toString()

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, s)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)

        }
        shareViaEmail.setOnClickListener {
            sendEmail()
           /** Toast.makeText(applicationContext, "Before Built Toast!", Toast.LENGTH_LONG).show()
            true
             mailgun = Mailgun.Builder("sandbox2f00d824157d4b06b1beab9a674df934.mailgun.org", "5b95bc3e4b7de13a4b10e00b103818d9-bbbc8336-b45c0f5d")
                .build()
            Toast.makeText(applicationContext, "Built Toast!", Toast.LENGTH_LONG).show()
            true
             //Send Email
            val from = Contact("fk_mokrane@esi.dz", null)
            Toast.makeText(applicationContext, "From Toast!", Toast.LENGTH_LONG).show()
            true
            val to = mutableListOf<Contact>()
            to.add(Contact("kaouthar.lee@gmail.com", null))
            Toast.makeText(applicationContext, "To Toast!", Toast.LENGTH_LONG).show()
            true
            requestBuilder = SendMessageRequest.Builder(from)
                .to(to)
                .text("Hi, Sent from TDM android")
                .subject("TDM2 Email")
**/
           //val response = mailgun.sendMessage(requestBuilder.build()).blockingGet()
             //.blockingGet()


        }
        shareViaSms.setOnClickListener {

                       pickContact()


            }

            /***val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
            startActivityForResult(intent, 1)


            Toast.makeText(applicationContext, "Showing Share Toast!", Toast.LENGTH_LONG).show()
            true***/
        }


       private fun pickContact() {
    Intent(Intent.ACTION_PICK, Uri.parse("content://contacts")).also { pickContactIntent ->
        pickContactIntent.type = CONTENT_TYPE // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST)
    }}
   /** fun sendEmail() {

        Log.i("Send email", "")
        val TO = arrayOf("")
        val CC = arrayOf("")
        val emailbody = "This is the mail Body Baby girl"
        val emailtitle = "This is the mail subject Sweet girl"
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.type = "text/plain"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO)
        emailIntent.putExtra(Intent.EXTRA_CC, CC)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailtitle)
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailbody)

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."))
            finish()
            Log.i("Finished sending email", "")
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(
                applicationContext,
                "There is no email client installed.", Toast.LENGTH_SHORT
            ).show()
        }

    }
**/
    // ---sends an SMS message to another device---
    fun sendSMS(phoneNumber: String, message: String) {

        val smsManager = SmsManager.getDefault()

        smsManager.sendTextMessage(phoneNumber, null, message, null, null)
        Toast.makeText(
            applicationContext,
            "At the end of sendSMS Papi", Toast.LENGTH_SHORT
        ).show()
    }


    /***
     *
     * ***/
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        // Check which request it is that we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                // We only need the NUMBER column, because there will be only one row in the result
                val projection: Array<String> = arrayOf(Phone.NUMBER)

                // Get the URI that points to the selected contact
                if (data != null) {
                    data.data?.also { contactUri ->
                        // Perform the query on the contact to get the NUMBER column
                        // We don't need a selection or sort order (there's only one result for this URI)
                        // CAUTION: The query() method should be called from a separate thread to avoid
                        // blocking your app's UI thread. (For simplicity of the sample, this code doesn't
                        // do that.)
                        // Consider using <code><a href="/reference/android/content/CursorLoader.html">CursorLoader</a></code> to perform the query.
                        contentResolver.query(contactUri, projection, null, null, null)?.apply {
                            moveToFirst()

                            // Retrieve the phone number from the NUMBER column
                            val column: Int = getColumnIndex(Phone.NUMBER)
                            val number: String = getString(column)
                            Toast.makeText(
                                applicationContext,
                                number, Toast.LENGTH_SHORT
                            ).show()
                            // Do something with the phone number...
                            if (ContextCompat.checkSelfPermission(this@ShareDataActivity, android.Manifest.permission.SEND_SMS)
                                != PackageManager.PERMISSION_GRANTED
                            ) {
                                // Permission is not granted
                                ActivityCompat.requestPermissions(
                                    this@ShareDataActivity,
                                    arrayOf(android.Manifest.permission.SEND_SMS), SEND_SMS_PERMISSION_REQUEST_CODE
                                )

                                Log.i("msg", "pas de permission")

                            } else {
                                sendSMS(number, "Hi from android")
                            }


                            /**if (ContextCompat.checkSelfPermission(
                                    this@ShareDataActivity,
                                    android.Manifest.permission.SEND_SMS
                                )
                                != PackageManager.PERMISSION_GRANTED
                            ) {
                                // Permission is not granted
                                ActivityCompat.requestPermissions(
                                    this@ShareDataActivity,
                                    arrayOf(android.Manifest.permission.SEND_SMS), SEND_SMS_PERMISSION_REQUEST_CODE
                                )

                                Log.i("msg", "pas de permissinon")

                            } else {
                                if (number != null) {
                                    sendSMS(number, "Hi from android")
                                }

                            }**/
                            /**
                            if (resultCode == Activity.RESULT_OK) {
                            val contactData = data!!.data
                            val c = contentResolver.query(contactData!!, null, null, null, null)
                            if (c!!.moveToFirst()) {

                            var phoneNumber = ""
                            var emailAddress = ""
                            val name = c.getString(c.getColumnIndex(Contacts.DISPLAY_NAME))
                            val contactId = c.getString(c.getColumnIndex(Contacts._ID))
                            //http://stackoverflow.com/questions/866769/how-to-call-android-contacts-list   our upvoted answer

                            var hasPhone = c.getString(c.getColumnIndex(Contacts.HAS_PHONE_NUMBER))

                            if (hasPhone.equals("1", ignoreCase = true))
                            hasPhone = "true"
                            else
                            hasPhone = "false"

                            if (java.lang.Boolean.parseBoolean(hasPhone)) {
                            val phones = contentResolver.query(
                            CONTENT_URI,
                            null,
                            CONTACT_ID + " = " + contactId,
                            null,
                            null
                            )
                            while (phones!!.moveToNext()) {
                            phoneNumber =
                            phones.getString(phones.getColumnIndex(NUMBER))
                            }
                            phones.close()
                            }

                            // Find Email Addresses
                            val emails = contentResolver.query(
                            Email.CONTENT_URI,
                            null,
                            Email.CONTACT_ID + " = " + contactId,
                            null,
                            null
                            )
                            while (emails!!.moveToNext()) {
                            emailAddress = emails.getString(emails.getColumnIndex(Email.DATA))
                            }
                            emails.close()

                            //mainActivity.onBackPressed();
                            // Toast.makeText(mainactivity, "go go go", Toast.LENGTH_SHORT).show();


                            //   sendSMS(phoneNumber,"Hi from android")
                            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS)
                            != PackageManager.PERMISSION_GRANTED
                            ) {
                            // Permission is not granted
                            ActivityCompat.requestPermissions(
                            this,
                            arrayOf(android.Manifest.permission.SEND_SMS), SEND_SMS_PERMISSION_REQUEST_CODE
                            )

                            Log.i("msg", "pas de permissinon")

                            } else {
                            sendSMS(phoneNumber, "Hi from android")
                            }

                            // val smsManager = SmsManager.getDefault()
                            // smsManager.sendTextMessage(phoneNumber, null,"Hhhhh", null, null)

                            // Log.d("curs", "$name num$phoneNumber mail$emailAddress")
                            }
                            c.close()
                            }
                            }

                             **/
                        }
                    }
                }}}}
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
                val emailId = "fk_mokrane@esi.dz"
                //Setting sender address
                mm.setFrom(InternetAddress(Credentials.EMAIL))
                //Adding receiver
                mm.addRecipient(
                    Message.RecipientType.TO,
                    InternetAddress(emailId))
                //Adding subject
                mm.subject = "Article Subject"
                //Adding message
                mm.setText("Article Body. from TDM2")

                //Sending email
                Transport.send(mm)

                appExecutors.mainThread().execute {
                    //Something that should be executed on main thread.
                }

            } catch (e: MessagingException) {
                e.printStackTrace()
            }
        }
    }}