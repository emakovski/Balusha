package com.egor.balusha.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.egor.balusha.R
import com.egor.balusha.activities.main.view.MainActivity
import com.egor.balusha.databinding.PetsBioBinding
import com.egor.balusha.util.BalushaApplication
import com.egor.balusha.util.Prefs


class PetBio : AppCompatActivity() {

    private lateinit var binding: PetsBioBinding
    private val prefs: Prefs = BalushaApplication.prefs!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PetsBioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        setListener()
    }

    private fun initView(){
        with(binding) {
            if (prefs.petPhoto.isNullOrBlank()) {
                photoOfDogInBio.setImageResource(R.drawable.no_photo)
            } else Glide.with(applicationContext).load(prefs.petPhoto).into(photoOfDogInBio)

            nameOfDogInBio.text = prefs.petName
            dateOfBirthInBio.text = prefs.petBirth
            sexInBio.text = prefs.petSex
            breedOfDogInBio.text = prefs.petBreed
            colorOfDogInBio.text = prefs.petColor
            tagNumberInBio.text = prefs.petTag
            marksInBio.text = prefs.petMarks
            pedigreeNumberInBio.text = prefs.petPedigree
            chipNumberInBio.text = prefs.petChipNumb
            chippingDateInBio.text = prefs.petChipDate
            chipLocationInBio.text = prefs.petChipLoc
            commentInBio.text = prefs.petComment
            if (prefs.petMarks.isNullOrBlank()){
                marksInBio.visibility = View.GONE
            }
        }
    }

    private fun setListener() {
        binding.buttonBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.buttonOwnerInfo.setOnClickListener {
            startActivity(Intent(this, OwnerBio::class.java))
        }
        binding.fabInBio.setOnClickListener {
            startActivity(Intent(this, PetInfoEdit::class.java))
        }
    }
}