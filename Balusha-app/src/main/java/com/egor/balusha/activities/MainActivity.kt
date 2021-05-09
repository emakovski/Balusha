package com.egor.balusha.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.egor.balusha.BottomNavFragment
import com.egor.balusha.R
import com.egor.balusha.databinding.ActivityMainBinding
import com.egor.balusha.databinding.OwnersBioBinding
import com.egor.balusha.dbpets.DatabasePetsInfo
import com.egor.balusha.dbpets.PetsInfoDao
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

private const val FIRSTRUN_ACTIVITY_CODE = 1
private const val MAIN_ACTIVITY_CODE = 2

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabasePetsInfo
    private lateinit var petsInfoDao: PetsInfoDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDatabase()
//        getSharedPreferences("FIRST_RUN_PREF", 0).edit().clear().apply()
        val isFirstRun = getSharedPreferences("FIRST_RUN_PREF", Context.MODE_PRIVATE).getBoolean("isFirstRun", true)
        if (isFirstRun) {
            //show sign up activity
            startActivity(Intent(this@MainActivity, OwnerInfoAdd::class.java))
        }
        getSharedPreferences("FIRST_RUN_PREF", Context.MODE_PRIVATE).edit().putBoolean("isFirstRun", false).apply()
        setSupportActionBar(bottom_app_bar)
        setFabListener()
        if (petsInfoDao.getPathToPic().isEmpty()) binding.photoOfDog.setImageResource(
            R.drawable.no_photo
        )
        else Glide.with(this).load(petsInfoDao.getPathToPic()).into(binding.photoOfDog)
        binding.nameOfDog.text = petsInfoDao.getName()
        binding.breedOfDog.text = petsInfoDao.getBreed()
        binding.birthOfDog.text = petsInfoDao.getDateOfBirth()
        binding.sexOfDog.text = petsInfoDao.getSex()
        binding.comment.text = petsInfoDao.getComment()
    }

    private fun initDatabase() {
        database = DatabasePetsInfo.getDataBase(this)
        petsInfoDao = database.getPetsInfoDao()
    }
    private fun setFabListener() {
        binding.fabMain.setOnClickListener {val bottomNavDrawerFragment =
            BottomNavFragment()
            bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
        }
    }

}