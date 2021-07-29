package com.egor.balusha.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.egor.balusha.R
import com.egor.balusha.saveImage
import com.google.android.material.floatingactionbutton.FloatingActionButton
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


class OwnerInfoAdd : AppCompatActivity(){
    private lateinit var textName: EditText
    private lateinit var textSurname: EditText
    private lateinit var textCountry: EditText
    private lateinit var textCity: EditText
    private lateinit var textStreet: EditText
    private lateinit var textHouse: EditText
    private lateinit var textBuilding: EditText
    private lateinit var textApartment: EditText
    private lateinit var textPhoneNumber: EditText
    private lateinit var textEmail: EditText
    private lateinit var fab: FloatingActionButton
    private lateinit var ownersPhoto: ImageView
    private lateinit var noOwnersPhoto: TextView
    private var photoWasLoaded: Boolean = false
    private lateinit var ownerPictureDirectory: File
    private lateinit var pathToPicture: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.owners_bio_add)
        ownersPhoto = findViewById(R.id.photo_of_owner_in_bio_add)
        textName = findViewById(R.id.name_of_owner_in_bio_add)
        textSurname = findViewById(R.id.surname_of_owner_in_bio_add)
        textCountry = findViewById(R.id.country_in_bio_add)
        textCity = findViewById(R.id.city_in_bio_add)
        textStreet = findViewById(R.id.street_in_bio_add)
        textHouse = findViewById(R.id.house_in_bio_add)
        textBuilding = findViewById(R.id.building_in_bio_add)
        textApartment = findViewById(R.id.apt_in_bio_add)
        textPhoneNumber = findViewById(R.id.phone_number_in_bio_add)
        textEmail = findViewById(R.id.email_in_bio_add)
        fab = findViewById(R.id.fab_in_owners_bio_add)
        noOwnersPhoto = findViewById(R.id.no_photo_owner_add)
        createDirectoryForOwnerPicture()
        setListeners()
    }

    private fun setListeners() {
        ownersPhoto.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_CODE_OWNER_PHOTO)
        }
        fab.setOnClickListener {
            addOwnerInfoAndGoToNextActivity()
        }
    }

    private fun addOwnerInfoAndGoToNextActivity() {
        if (!photoWasLoaded) {
            pathToPicture = ""
        }
        val name = textName.text.toString()
        val surname = textSurname.text.toString()
        val country = textCountry.text.toString()
        val city = textCity.text.toString()
        val street = textStreet.text.toString()
        val house = textHouse.text.toString()
        val building = textBuilding.text.toString()
        val apartment = textApartment.text.toString()
        val phone = textPhoneNumber.text.toString()
        val email = textEmail.text.toString()
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
            startActivity(Intent(this, PetInfoAdd::class.java))
            }
        else {
            Toast.makeText(this, "Fields can't be empty", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.extras?.get("data")?.run {
            pathToPicture = saveImage(this as Bitmap, ownersPhoto, ownerPictureDirectory)
            photoWasLoaded = true
            noOwnersPhoto.visibility = View.INVISIBLE
        }
    }

    private fun createDirectoryForOwnerPicture() {
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            ownerPictureDirectory = File("${getExternalFilesDir(Environment.DIRECTORY_PICTURES)}/OwnersPicture")
            if (!ownerPictureDirectory.exists()) {
                ownerPictureDirectory.mkdir()
            }
        }
//        createOwnersDirectory(applicationContext)?.run {
//            ownerPictureDirectory = this
//        }
    }
}