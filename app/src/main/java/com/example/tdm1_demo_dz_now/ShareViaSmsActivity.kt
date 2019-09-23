package com.example.tdm1_demo_dz_now

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_detail_news.*

import kotlinx.android.synthetic.main.activity_share_via_sms.*
import kotlinx.android.synthetic.main.content_share_via_sms.*

class ShareViaSmsActivity : AppCompatActivity() {
     lateinit var message:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_via_sms)
        setSupportActionBar(toolbar)


        if (intent!=null)
            if(!intent.getStringExtra("webURL").isEmpty())
                message="J'ai aimé cet article, jettez un coup d'oeil:" + intent.getStringExtra("webURL")

               //message=message + intent.getStringExtra("webURL").toString()
               smsBody.setText(message)
        btn_select_contact.setOnClickListener {
            pickContact()
        }
    }
    private fun pickContact() {
        Intent(Intent.ACTION_PICK, Uri.parse("content://contacts")).also { pickContactIntent ->
            pickContactIntent.type =
                ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE // Show user only contacts w/ phone numbers
            startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST)
        }}
    // ---sends an SMS message to another device---
    fun sendSMS(phoneNumber: String, message: String) {

        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(phoneNumber, null, message, null, null)
        Toast.makeText(
            applicationContext,
            "SMS envoyé", Toast.LENGTH_SHORT
        ).show()
    }
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        // Check which request it is that we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                // We only need the NUMBER column, because there will be only one row in the result
                val projection: Array<String> = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)

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
                            val column: Int = getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            val number: String = getString(column)
                            Toast.makeText(
                                applicationContext,
                                number, Toast.LENGTH_SHORT
                            ).show()
                            // Do something with the phone number...
                            if (ContextCompat.checkSelfPermission(this@ShareViaSmsActivity, android.Manifest.permission.SEND_SMS)
                                != PackageManager.PERMISSION_GRANTED
                            ) {
                                // Permission is not granted
                                ActivityCompat.requestPermissions(
                                    this@ShareViaSmsActivity,
                                    arrayOf(android.Manifest.permission.SEND_SMS), SEND_SMS_PERMISSION_REQUEST_CODE
                                )

                                Log.i("msg", "pas de permission")

                            } else {
                                message=smsBody.text.toString()
                                sendSMS(number, message)
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

}
