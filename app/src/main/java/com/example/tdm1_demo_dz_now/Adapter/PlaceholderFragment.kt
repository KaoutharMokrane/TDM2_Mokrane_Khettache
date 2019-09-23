package com.example.tdm1_demo_dz_now.Adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tdm1_demo_dz_now.Interface.NewsService
import com.example.tdm1_demo_dz_now.R
import kotlinx.android.synthetic.main.card_custom_view.view.*
import kotlinx.android.synthetic.main.fragment_news.view.*

/**
 * A placeholder fragment containing a simple view.
 */

class PlaceholderFragment (): Fragment() {
    var cpt = 1
    var tet : String?=null
    private lateinit var mService: NewsService
    lateinit var adapter : ListNewsAdapter
    private lateinit var layoutManager: androidx.recyclerview.widget.LinearLayoutManager

    @SuppressLint("ValidFragment")
    constructor(tet:String) : this() {
        this.tet=tet
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_news, container, false)
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