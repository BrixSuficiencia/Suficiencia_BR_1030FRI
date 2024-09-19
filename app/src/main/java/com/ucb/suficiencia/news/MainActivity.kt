package com.ucb.suficiencia.news

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ucb.suficiencia.news.R


class MainActivity : AppCompatActivity() {
    private var isLandscape = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        isLandscape =
            getResources().configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
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
        if (isLandscape) {
            val contentFragment =
                supportFragmentManager.findFragmentById(R.id.fragment_news_content) as NewsContentFragment?
            contentFragment?.updateContent("Content for $headline")
        } else {
            val contentFragment = NewsContentFragment()
            val args = Bundle()
            args.putString("headline", headline)
            contentFragment.setArguments(args)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, contentFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}

