package com.example.tdm1_demo_dz_now.Data

import androidx.lifecycle.LiveData
import androidx.room.*
import retrofit2.http.DELETE
import androidx.room.Delete



@Dao
interface ArticleDao {

    @Query("SELECT * from article_table" )
    fun getArticles(): List<ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(article: ArticleEntity)

    @Query("DELETE FROM article_table")
    fun deleteAll():Int

    @Delete
    fun deleteArticle(article: ArticleEntity)
}