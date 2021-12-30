package com.egor.balusha.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.egor.balusha.R
import com.egor.balusha.activities.main.view.MainActivity
import com.egor.balusha.createOwnersDirectory
import com.egor.balusha.databinding.OwnersBioEditBinding
import com.egor.balusha.saveImage
import java.io.File

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

class OwnerInfoEdit : AppCompatActivity() {

    private lateinit var binding: OwnersBioEditBinding
    private var photoWasLoaded: Boolean = true
    private lateinit var ownerPictureDirectory: File
    private lateinit var pathToPicture: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OwnersBioEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val prefs: SharedPreferences = getSharedPreferences("owners_info", Context.MODE_PRIVATE)
        val path = prefs.getString(OWNER_PHOTO, "no photo")
        val file = File(path)
        if (file.exists()) {
            if (path == "") {
                binding.photoOfOwnerInBioEdit.setImageResource(R.drawable.no_photo)
            } else {
                Glide.with(this).load(path).into(binding.photoOfOwnerInBioEdit)
                photoWasLoaded = true
                if (path != null) {
                    pathToPicture = path
                }
            }
        }
        binding.nameOfOwnerInBioEdit.setText(prefs.getString(OWNER_NAME,"not found"))
        binding.surnameOfOwnerInBioEdit.setText(prefs.getString(OWNER_SURNAME,"not found"))
        binding.countryInBioEdit.setText(prefs.getString(OWNER_COUNTRY,"not found"))
        binding.cityInBioEdit.setText(prefs.getString(OWNER_CITY,"not found"))
        binding.streetInBioEdit.setText(prefs.getString(OWNER_STREET,"not found"))
        binding.houseInBioEdit.setText(prefs.getString(OWNER_HOUSE,"not found"))
        binding.buildingInBioEdit.setText(prefs.getString(OWNER_BUILDING,"not found"))
        binding.aptInBioEdit.setText(prefs.getString(OWNER_APARTMENT,"not found"))
        binding.phoneNumberInBioEdit.setText(prefs.getString(OWNER_PHONE,"not found"))
        binding.emailInBioEdit.setText(prefs.getString(OWNER_EMAIL,"not found"))
        createDirectoryForOwnerPicture()
        setListeners()
    }

    private fun setListeners() {
        binding.photoOfOwnerInBioEdit.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_CODE_OWNER_PHOTO)
        }
        binding.fabInOwnersBioEdit.setOnClickListener {
            editOwnerInfoAndGoToNextActivity()
        }
        binding.buttonBackToMenuOwnersBioEdit.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun editOwnerInfoAndGoToNextActivity() {
        if (!photoWasLoaded) {
            pathToPicture = ""
        }
        val name = binding.nameOfOwnerInBioEdit.text.toString()
        val surname = binding.surnameOfOwnerInBioEdit.text.toString()
        val country = binding.countryInBioEdit.text.toString()
        val city = binding.cityInBioEdit.text.toString()
        val street = binding.streetInBioEdit.text.toString()
        val house = binding.houseInBioEdit.text.toString()
        val building = binding.buildingInBioEdit.text.toString()
        val apartment = binding.aptInBioEdit.text.toString()
        val phone = binding.phoneNumberInBioEdit.text.toString()
        val email = binding.emailInBioEdit.text.toString()
        if (name.isNotEmpty() && surname.isNotEmpty() && country.isNotEmpty() && city.isNotEmpty() && street.isNotEmpty() && house.isNotEmpty() && building.isNotEmpty() && apartment.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty()) {
            getSharedPreferences("owners_info", Context.MODE_PRIVATE)
                .edit()
                .apply {
                    putString(OWNER_PHOTO, pathToPicture)
                    putString(OWNER_NAME, name)
                    putString(OWNER_SURNAME, surname)
                    putString(OWNER_COUNTRY, country)
                    putString(OWNER_CITY, city)
                    putString(OWNER_STREET, street)
                    putString(OWNER_HOUSE, house)
                    putString(OWNER_BUILDING, building)
                    putString(OWNER_APARTMENT, apartment)
                    putString(OWNER_PHONE, phone)
                    putString(OWNER_EMAIL, email)
                }
                .apply()
            startActivity(Intent(this, OwnerBio::class.java))
        } else {
            Toast.makeText(this, "Fields can't be empty", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (data != null) {
//            if (data.extras != null) {
//                if (data.extras?.get("data") != null) {
//                    val photo = data.extras?.get("data") as Bitmap?
//                    if (photo != null) {
//                        pathToPicture = saveImage(photo, binding.photoOfOwnerInBioEdit, ownerPictureDirectory)
//                        photoWasLoaded = true
//                    }
//                }
//            }
//        }
        super.onActivityResult(requestCode, resultCode, data)
        data?.extras?.get("data")?.run {
            pathToPicture = saveImage(this as Bitmap, binding.photoOfOwnerInBioEdit, ownerPictureDirectory)
            photoWasLoaded = true
        }
    }

    private fun createDirectoryForOwnerPicture() {
//        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
//            ownerPictureDirectory = File("${getExternalFilesDir(Environment.DIRECTORY_PICTURES)}/OwnersPicture")
//            if (!ownerPictureDirectory.exists()) {
//                ownerPictureDirectory.mkdir()
//            }
//        }
        createOwnersDirectory(applicationContext)?.run {
            ownerPictureDirectory = this
        }
    }
}