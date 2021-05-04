package com.egor.balusha.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.egor.balusha.BottomNavFragment
import com.egor.balusha.R
import com.egor.balusha.dbpets.DatabasePetsInfo
import com.egor.balusha.dbpets.PetsInfoDao
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

private const val FIRSTRUN_ACTIVITY_CODE = 1
private const val MAIN_ACTIVITY_CODE = 2

class MainActivity : AppCompatActivity() {
    private lateinit var fab: FloatingActionButton
    private lateinit var database: DatabasePetsInfo
    private lateinit var petsInfoDao: PetsInfoDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDatabase()
//        getSharedPreferences("FIRST_RUN_PREF", 0).edit().clear().apply()
        val isFirstRun = getSharedPreferences("FIRST_RUN_PREF", Context.MODE_PRIVATE).getBoolean("isFirstRun", true)
        if (isFirstRun) {
            //show sign up activity
            startActivity(Intent(this@MainActivity, OwnerInfoAdd::class.java))
        }
        getSharedPreferences("FIRST_RUN_PREF", Context.MODE_PRIVATE).edit().putBoolean("isFirstRun", false).apply()
        setContentView(R.layout.activity_main)
        fab=findViewById(R.id.fab_main)
        setSupportActionBar(bottom_app_bar)
        setFabListener()
    }

    private fun initDatabase() {
        database = DatabasePetsInfo.getDataBase(this)
        petsInfoDao = database.getPetsInfoDao()
    }
    private fun setFabListener() {
        fab.setOnClickListener {val bottomNavDrawerFragment =
            BottomNavFragment()
            bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
        }
    }

}