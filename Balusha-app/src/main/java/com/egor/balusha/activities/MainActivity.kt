package com.egor.balusha.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.egor.balusha.BottomNavFragment
import com.egor.balusha.DatabaseRepository
import com.egor.balusha.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

private const val PET_PHOTO = "pet_photo"
private const val PET_NAME = "pet_name"
private const val PET_BIRTH = "pet_birth"
private const val PET_SEX = "pet_sex"
private const val PET_BREED = "pet_breed"
private const val PET_COMMENT = "pet_comment"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: DatabaseRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        DatabaseRepository.initDatabase(applicationContext)
        repository = DatabaseRepository()
//       getSharedPreferences("FIRST_RUN_PREF", 0).edit().clear().apply()
        val isFirstRun = getSharedPreferences("FIRST_RUN_PREF", Context.MODE_PRIVATE).getBoolean("isFirstRun", true)
        if (isFirstRun) {
            //show sign up activity
            startActivity(Intent(this@MainActivity, OwnerInfoAdd::class.java))
        }
        getSharedPreferences("FIRST_RUN_PREF", Context.MODE_PRIVATE).edit().putBoolean("isFirstRun", false).apply()
        setSupportActionBar(bottom_app_bar)
        setClickListeners()
        val prefs: SharedPreferences = getSharedPreferences("pets_info", Context.MODE_PRIVATE)
//        if (prefs.getString(PET_PHOTO,"").isEmpty()) binding.photoOfDog.setImageResource(
//            R.drawable.no_photo
//        )
//        else
            Glide.with(this).load(prefs.getString(PET_PHOTO, "no photo")).into(binding.photoOfDog)
        binding.nameOfDog.text=prefs.getString(PET_NAME,"not found")
        binding.birthOfDog.text=prefs.getString(PET_BIRTH,"not found")
        binding.sexOfDog.text=prefs.getString(PET_SEX,"not found")
        binding.breedOfDog.text=prefs.getString(PET_BREED,"not found")
        binding.comment.text=prefs.getString(PET_COMMENT,"not found")
    }

    private fun setClickListeners() {
        binding.fabMain.setOnClickListener {val bottomNavDrawerFragment =
            BottomNavFragment()
            bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
        }
        binding.buttonVaccine.setOnClickListener {
            val intent = Intent(this, VaccinationList::class.java)
            startActivity(intent)
        }
        binding.buttonHelminths.setOnClickListener {
            val intent = Intent(this, HelminthTreatList::class.java)
            startActivity(intent)
        }
        binding.buttonFleas.setOnClickListener {
            val intent = Intent(this, FleasTicksList::class.java)
            startActivity(intent)
        }
    }

}