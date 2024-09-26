package com.ucb.suficiencia.menuapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_MenuApp)

        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Menu App"
        supportActionBar?.subtitle = "Choose an Option"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_first -> {
                navigateToFragment(FirstFragment())
                return true
            }
            R.id.menu_dialog -> {
                val dialogFragment = CustomDialogFragment()
                dialogFragment.show(supportFragmentManager, "customDialog")
                return true
            }
            R.id.menu_exit -> {
                finish()
                return true
            }
            android.R.id.home -> {
                // Handle back navigation for the action bar
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null) // Ensure fragment is added to back stack
        transaction.commit()
    }

    override fun onBackPressed() {
        // Check if a dialog is currently displayed
        val dialogFragment = supportFragmentManager.findFragmentByTag("customDialog")
        if (dialogFragment != null && dialogFragment.isVisible) {
            (dialogFragment as CustomDialogFragment).dismiss() // Dismiss the dialog if it is open
        } else {
            // If there are fragments in the back stack, pop the last one
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            } else {
                // No fragments in the back stack, call super
                super.onBackPressed()
            }
        }
    }
}
