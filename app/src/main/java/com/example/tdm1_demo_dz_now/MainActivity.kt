package com.example.tdm1_demo_dz_now

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

       //var infos =intent.getStringExtra("INFO_User")

        //initialize view
       // textViewName.text=getString(R.string.nav_header_title)



        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_articles -> {
                val intent = Intent(this@MainActivity,newsAct::class.java)
                startActivity(intent)
            }
            R.id.nav_themes -> {
                val intent = Intent(this@MainActivity,SimpleActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_sites -> {
                val intent = Intent(this@MainActivity,SourceActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_sauvgardes -> {
                var intentSaved = Intent(this,SavedActivity::class.java)
                startActivity(intentSaved)
            }
            R.id.nav_signets -> {
                var intentSaved = Intent(this,SignetsActivity::class.java)
                startActivity(intentSaved)
            }
            R.id.nav_videos -> {

            }
            R.id.nav_sing_out-> {
                val intent = Intent(this@MainActivity,LoginActivity::class.java)
                startActivity(intent)
                FirebaseAuth.getInstance().signOut()
            }


        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


}

