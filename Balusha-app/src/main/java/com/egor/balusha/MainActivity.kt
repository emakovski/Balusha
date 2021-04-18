package com.egor.balusha

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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