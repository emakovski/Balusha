package com.egor.balusha.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.egor.balusha.databinding.OwnersBioBinding


private const val REQUEST_CODE_OWNER_PHOTO = 2
private const val OWNER_PHOTO = "photo"
private const val OWNER_NAME = "name"
private const val OWNER_SURNAME = "surname"
private const val OWNER_COUNTRY = "country"
private const val OWNER_CITY = "city"
private const val OWNER_STREET = "street"
private const val OWNER_HOUSE = "house"
private const val OWNER_BUILDING = "building"
private const val OWNER_APARTMENT = "apartment"
private const val OWNER_PHONE = "phone"
private const val OWNER_EMAIL = "email"

class OwnerBio : AppCompatActivity(){

    private lateinit var binding: OwnersBioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OwnersBioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val prefs: SharedPreferences = getSharedPreferences("owners_info", Context.MODE_PRIVATE)
        binding.nameOfOwnerInBio.text=prefs.getString(OWNER_NAME,"not found")
        binding.surnameOfOwnerInBio.text=prefs.getString(OWNER_SURNAME,"not found")
        binding.countryInBio.text=prefs.getString(OWNER_COUNTRY,"not found")
        binding.cityInBio.text=prefs.getString(OWNER_CITY,"not found")
        binding.streetInBio.text=prefs.getString(OWNER_STREET,"not found")
        binding.houseInBio.text=prefs.getString(OWNER_HOUSE,"not found")
        binding.buildingInBio.text=prefs.getString(OWNER_BUILDING,"not found")
        binding.aptInBio.text=prefs.getString(OWNER_APARTMENT,"not found")
        binding.phoneNumberInBio.text=prefs.getString(OWNER_PHONE,"not found")
        binding.emailInBio.text=prefs.getString(OWNER_EMAIL,"not found")

    }
}