package com.ucb.suficiencia.news

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var isLandscape = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        isLandscape =
            resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        if (isLandscape) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_headline, HeadlineListFragment())
                .replace(R.id.fragment_news_content, NewsContentFragment())
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HeadlineListFragment())
                .commit()
        }
    }

    fun showNewsContent(headline: String) {
        val content: String
        val imageResId: Int

        // Define content and corresponding image for each headline
        when (headline) {
            "Breaking News: Market Hits All-Time High" -> {
                content = "The stock market reached a new peak today, driven by tech stocks."
                imageResId = R.drawable.stonks
            }
            "Local Man Gets Hit by Truck, Wakes Up in Fantasy World" -> {
                content = "In a bizarre turn of events, a local man claims he was hit by a truck and woke up in a world filled with magic and adventure. He now has to defeat the Demon Lord to return home."
                imageResId = R.drawable.truck
            }
            "Teen Accidentally Summons Dragon While Doing Homework" -> {
                content = "A high school student, while struggling with a complex math problem, unknowingly summoned a dragon into his living room. The dragon, though terrifying at first, offered to help with his algebra."
                imageResId = R.drawable.dragon
            }
            else -> {
                content = "No content available."
                imageResId = R.drawable.default_image
            }
        }

        if (isLandscape) {
            val contentFragment =
                supportFragmentManager.findFragmentById(R.id.fragment_news_content) as NewsContentFragment?
            contentFragment?.updateContent(content, imageResId)
        } else {
            val contentFragment = NewsContentFragment()
            val args = Bundle()
            args.putString("content", content)
            args.putInt("imageResId", imageResId)
            contentFragment.arguments = args
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, contentFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
