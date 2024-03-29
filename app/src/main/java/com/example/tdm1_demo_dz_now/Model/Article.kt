package com.example.tdm1_demo_dz_now.Model


class Article {

    var author: String? = null
    var url: String? = null
    var title: String? = null
    var source: Source?=null
    var urlToImage:String?=null
    var content: String?=null
    var publishedAt:String?=null
    var description:String?=null
    var saved:Boolean?=false
    constructor() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    constructor(author: String, url: String, title: String,source:Source,description: String) {
        this.author = author
        this.url = url
        this.title = title
        this.source=source
        this.description=description
    }
}