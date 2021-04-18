package com.egor.balusha

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File

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
   // private lateinit var repository: DataBaseRepository

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
        repository = DataBaseRepository()
        createDirectoryForPictures()
        setSupportActionBar(toolbar)
        setListeners()
    }

    private fun setListeners() {
        imgButtonBack.setOnClickListener {
            backToPreviousActivity()
        }
        imgButtonApply.setOnClickListener {
            addCarInfoAndBackToPreviousActivity()
        }
        fab.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_CODE_PHOTO)
        }
    }

    private fun addCarInfoAndBackToPreviousActivity() {
        if (!photoWasLoaded) {
            pathToPicture = ""
        }
        val name = textName.text.toString()
        val producer = textProducer.text.toString()
        val model = textModel.text.toString()
        val plate = textPlate.text.toString()
        if (name.isNotEmpty() && producer.isNotEmpty() && model.isNotEmpty() && plate.isNotEmpty()) {
            val carInfo = CarInfo(pathToPicture, name, producer, model, plate)
            repository.addCar(carInfo).subscribe {
                setResult(Activity.RESULT_OK)
                finish()
            }
        } else {
            Toast.makeText(this, "Fields can't be empty", Toast.LENGTH_SHORT).show()
        }
    }

    private fun backToPreviousActivity() {
        setResult(RESULT_CODE_BUTTON_BACK, intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (data.extras != null) {
                if (data.extras?.get("data") != null) {
                    val photo = data.extras?.get("data") as Bitmap?
                    if (photo != null) {
                        pathToPicture = saveImage(photo, carPhoto, carPictureDirectory)
                        photoWasLoaded = true
                        noCarPhoto.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    private fun createDirectoryForPictures() {
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            carPictureDirectory = File("${getExternalFilesDir(Environment.DIRECTORY_PICTURES)}/CarPictures")
            if (!carPictureDirectory.exists()) {
                carPictureDirectory.mkdir()
            }
        }
    }
}


}