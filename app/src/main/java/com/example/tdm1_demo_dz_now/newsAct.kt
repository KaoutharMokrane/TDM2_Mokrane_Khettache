package com.example.tdm1_demo_dz_now

import android.annotation.SuppressLint
import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.card_custom_view.view.*
import kotlinx.android.synthetic.main.fragment_news.view.*
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.constraintlayout.widget.Placeholder
import com.example.tdm1_demo_dz_now.Adapter.ListNewsAdapter
import com.example.tdm1_demo_dz_now.Adapter.SectionsPagerAdapter
import com.example.tdm1_demo_dz_now.Common.Common
import com.example.tdm1_demo_dz_now.Interface.NewsService
import com.example.tdm1_demo_dz_now.Model.News
import com.google.firebase.auth.FirebaseAuth
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.fragment_news.*
import retrofit2.Call
import retrofit2.Response

class newsAct : AppCompatActivity() {
    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        //mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        mSectionsPagerAdapter= SectionsPagerAdapter(supportFragmentManager).getInstance(supportFragmentManager)
        mSectionsPagerAdapter?.addFragment(PlaceholderFragment.newInstance(1,"general"),"general",0)
        mSectionsPagerAdapter?.addFragment(PlaceholderFragment.newInstance(2,"sport"),"sport",1)
        mSectionsPagerAdapter?.addFragment(PlaceholderFragment.newInstance(3,"technology"),"technology",2)
        mSectionsPagerAdapter?.addFragment(PlaceholderFragment.newInstance(4,"science"),"science",3)
        mSectionsPagerAdapter?.addFragment(PlaceholderFragment.newInstance(5,"health"),"health",4)
        mSectionsPagerAdapter?.addFragment(PlaceholderFragment.newInstance(1,"entertainment"),"entertainment",5)

        tabLayout.addTab(tabLayout.newTab().setText("Général"),0)
        tabLayout.addTab(tabLayout.newTab().setText("Sport"),1)

        tabLayout.addTab(tabLayout.newTab().setText("Technology"),2)
        tabLayout.addTab(tabLayout.newTab().setText("Science"),3)

        tabLayout.addTab(tabLayout.newTab().setText("Health"),4)
        tabLayout.addTab(tabLayout.newTab().setText("Autres"),5)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_news, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the LoginActivity/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Modifier les catégories ")
            val items = arrayOf("Politique", "International", "Sport", "Culture","Musique","Cinéma")
            val selectedList = ArrayList<Int>()
            builder.setMultiChoiceItems(items, null
            ) { _, which, isChecked ->
                if (isChecked) {
                    selectedList.add(which)
                } else if (selectedList.contains(which)) {
                    selectedList.remove(Integer.valueOf(which))
                }
            }

            builder.setPositiveButton("Enregistrer") { _, _ ->
                val selectedStrings = ArrayList<String>()

                for (j in selectedList.indices) {
                    selectedStrings.add(items[selectedList[j]])
                }

            }
            builder.show()

            return true
        }
        return super.onOptionsItemSelected(item)
    }


    class PlaceholderFragment (): Fragment() {
        var cpt = 1
        var category : String?=null
        private lateinit var mService: NewsService
        lateinit var adapter : ListNewsAdapter
        private lateinit var layoutManager: androidx.recyclerview.widget.LinearLayoutManager
        lateinit var  alertDialog: AlertDialog

        @SuppressLint("ValidFragment")
        constructor(category:String) : this() {
            this.category=category
        }
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val rootView = inflater.inflate(R.layout.fragment_news, container, false)

            alertDialog= SpotsDialog(context)
            alertDialog.show()
            mService= Common.newsService
            mService.getNewsCategory(Common.getNewsAPI(this.category!!))
                // mService.newsAct
                .enqueue(object : retrofit2.Callback<News> {
                    override fun onFailure(call: Call<News>, t: Throwable) {
                        alertDialog.dismiss()
                        Toast.makeText(context,"Failed check connexion ", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<News>, response: Response<News>) {
                        alertDialog.dismiss()

                        rootView.recycler_real_news.setHasFixedSize(true)
                        layoutManager= androidx.recyclerview.widget.LinearLayoutManager(context)
                        rootView.recycler_real_news.layoutManager=layoutManager!!

                        var adapter = ListNewsAdapter(response.body()!!.articles!!,context!!)
                        adapter.notifyDataSetChanged()
                        rootView.recycler_real_news.adapter = adapter
                    }

                })

            return rootView

        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private var ARG_SECTION_NUMBER = "section_number"
            private var LIST_TITLE=  "listTitle"

            /**
             *
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int, listTitle : String): PlaceholderFragment {
                val fragment = PlaceholderFragment(listTitle)
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                args.putString(LIST_TITLE,listTitle)
                fragment.arguments = args
                return fragment
            }
        }
    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    /*   inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
           private val mFragmentList = ArrayList<Fragment>()
           private val mFragmentTitleList = ArrayList<String>()
         private  val tabLayoutString = ArrayList<String>()
           override fun getItem(position: Int): Fragment {
               // getItem is called to instantiate the fragment for the given page.
               // Return a PlaceholderFragment (defined as a static inner class below).
               tabLayout.addTab(tabLayout.newTab().setText("Tab 1 Item"))

               mFragmentTitleList.add("hi")
               mFragmentTitleList.add("hELLO")
               mFragmentTitleList.add("hSGG")
               mFragmentTitleList.add("hGSG")
               mFragmentTitleList.add("hSG")


               return PlaceholderFragment.newInstance(position + 1,mFragmentTitleList.get(position))
           }

           override fun getCount(): Int {
               // Show 4 total pages.
               return 4
           }
       }


       }*/




}
