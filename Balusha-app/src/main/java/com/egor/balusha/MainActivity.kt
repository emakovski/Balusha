package com.egor.balusha

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

private const val FIRSTRUN_ACTIVITY_CODE = 1
private const val MAIN_ACTIVITY_CODE = 2

class MainActivity : AppCompatActivity() {
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isFirstRun = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).getBoolean("isFirstRun", true)
        if (isFirstRun) {
            //show sign up activity
            startActivity(Intent(this@MainActivity, OwnerInfoAdd::class.java))
        }
        getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit().putBoolean("isFirstRun", false).apply()

        setContentView(R.layout.activity_main)
        fab=findViewById(R.id.fab_main)
        setSupportActionBar(bottom_app_bar)
        setFabListener()
    }
    private fun setFabListener() {
        fab.setOnClickListener {val bottomNavDrawerFragment = BottomNavFragment()
            bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
        }
    }

}