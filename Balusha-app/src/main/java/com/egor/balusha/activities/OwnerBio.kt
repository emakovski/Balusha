package com.egor.balusha.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.egor.balusha.R
import com.egor.balusha.activities.main.view.MainActivity
import com.egor.balusha.databinding.OwnersBioBinding
import com.egor.balusha.util.BalushaApplication
import com.egor.balusha.util.Prefs


class OwnerBio : AppCompatActivity(){

    private lateinit var binding: OwnersBioBinding
    private val prefs: Prefs = BalushaApplication.prefs!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OwnersBioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        setListener()
    }

    private fun initView(){
        with(binding) {

            if (prefs.ownerPhoto.isNullOrBlank()) {
                photoOfOwnerInBio.setImageResource(R.drawable.no_photo)
            } else Glide.with(applicationContext).load(prefs.ownerPhoto).into(photoOfOwnerInBio)

            nameOfOwnerInBio.text = prefs.ownerName
            surnameOfOwnerInBio.text = prefs.ownerSurname
            countryInBio.text = prefs.ownerCountry
            cityInBio.text = prefs.ownerCity
            streetInBio.text = prefs.ownerStreet
            houseInBio.text = prefs.ownerHouse
            buildingInBio.text = prefs.ownerBuilding
            aptInBio.text = prefs.ownerApartment
            phoneNumberInBio.text = prefs.ownerPhone
            emailInBio.text = prefs.ownerEmail
        }
    }

    private fun setListener() {
        binding.buttonBack.setOnClickListener {
            startActivity(Intent(this, PetBio::class.java))
        }
        binding.fabInOwnersBio.setOnClickListener {
            startActivity(Intent(this, OwnerInfoEdit::class.java))
        }
    }
}