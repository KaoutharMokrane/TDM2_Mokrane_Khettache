package com.example.tdm1_demo_dz_now

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {


    //1
    val RC_SIGN_IN: Int = 1
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions
    private lateinit var firebaseAuth: FirebaseAuth
    private var user : FirebaseUser?=null
    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, LoginActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        FirebaseApp.initializeApp(this)
        configureGoogleSignIn()
        setupUI()
        firebaseAuth = FirebaseAuth.getInstance()

        /*bg_image.setOnClickListener {
            val intent = Intent(this, newsAct::class.java)
            // start your next activity
            startActivity(intent)
        }*/
    }

    //To avoid the need for the user to sign in every time the app is launched it would be better
    // to check if the user is signed in already.
    // This can be achieved by checking if the current user is already signed in from within the onStart() method:

    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {

        var infos = Array<String>(5,{n:Int->""})
        infos[0]= user.uid.toString()
        infos[1]= user.email.toString()
        infos[2]= user.photoUrl.toString()
        infos[3]= user.displayName.toString()


        val intent = Intent(this@LoginActivity,MainActivity::class.java)
        intent.putExtra("INFO_USER",infos)
        startActivity(intent)
  //      startActivity(newsAct.getLaunchIntent(this))

    //        startActivity(newsAct.getLaunchIntent(this))
            finish()
        }
    }

    private fun configureGoogleSignIn() {
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.request_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions)
    }

    private fun setupUI() {
        google_button.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                 user = firebaseAuth.currentUser

                var infos = Array<String>(5,{n:Int->""})
                infos[0]= user!!.uid.toString()
                infos[1]= user!!.email.toString()
                infos[2]= user!!.photoUrl.toString()
                infos[3]= user!!.displayName.toString()

                val intent = Intent(this@LoginActivity,MainActivity::class.java)
                intent.putExtra("INFO_USER",infos)
                startActivity(intent)
                //startActivity(newsAct.getLaunchIntent(this))
            } else {
                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }
        }
    }
}