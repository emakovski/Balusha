package com.egor.balusha

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.egor.balusha.dbpets.DatabasePetsInfo
import com.egor.balusha.dbpets.PetsInfo
import com.egor.balusha.dbpets.PetsInfoDao
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File

private const val REQUEST_CODE_PHOTO = 1

class PetInfoAdd : AppCompatActivity(){
    private lateinit var petName: EditText
    private lateinit var petDateOfBirth: EditText
    private lateinit var petSex: EditText
    private lateinit var petBreed: EditText
    private lateinit var petColor: EditText
    private lateinit var petTagNumber: EditText
    private lateinit var petMarks: EditText
    private lateinit var petPedigreeNumb: EditText
    private lateinit var petChipNumb: EditText
    private lateinit var petChipDate: EditText
    private lateinit var petChipLoc: EditText
    private lateinit var petOwnersComm: EditText
    private lateinit var fab: FloatingActionButton
    private lateinit var switch: SwitchCompat
    private lateinit var petPhoto: ImageView
    private lateinit var noPetPhoto: TextView
    private var photoWasLoaded: Boolean = false
    private lateinit var petsPictureDirectory: File
    private lateinit var pathToPicture: String
    private lateinit var dataBase: DatabasePetsInfo
    private lateinit var petsInfoDao: PetsInfoDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pets_bio_add)
        petPhoto = findViewById(R.id.photo_of_dog_in_bio_add)
        petName = findViewById(R.id.name_of_dog_in_bio_add)
        petDateOfBirth = findViewById(R.id.date_of_birth_in_bio_add)
        petSex = findViewById(R.id.sex_in_bio_add)
        petBreed = findViewById(R.id.breed_of_dog_in_bio_add)
        petColor = findViewById(R.id.color_of_dog_in_bio_add)
        petTagNumber = findViewById(R.id.tag_number_in_bio_add)
        petMarks = findViewById(R.id.marks_in_bio_add)
        petPedigreeNumb = findViewById(R.id.pedigree_number_in_bio_add)
        petChipNumb = findViewById(R.id.chip_number_in_bio_add)
        petChipDate = findViewById(R.id.chipping_date_in_bio_add)
        petChipLoc = findViewById(R.id.chip_location_in_bio_add)
        petOwnersComm = findViewById(R.id.comment_in_bio_add)
        fab = findViewById(R.id.fab_in_pets_bio_add)
        switch = findViewById(R.id.switch_pets_add)
        noPetPhoto = findViewById(R.id.no_photo_pets_add)
        dataBase = DatabasePetsInfo.getDataBase(applicationContext)
        petsInfoDao = dataBase.getPetsInfoDao()
        createDirectoryForOwnerPicture()
        setListeners()
    }

    private fun setListeners() {
        petPhoto.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_CODE_PHOTO)
        }
        fab.setOnClickListener {
            addPetInfoAndGoToNextActivity()
        }
    }

    private fun addPetInfoAndGoToNextActivity() {
        if (!photoWasLoaded) {
            pathToPicture = ""
        }
        val name = petName.text.toString()
        val dateOfBirth = petDateOfBirth.text.toString()
        val sex = petSex.text.toString()
        val breed = petBreed.text.toString()
        val color = petColor.text.toString()
        val tagNumber = petTagNumber.text.toString()
        val marks = petMarks.text.toString()
        val pedigreeNumber = petPedigreeNumb.text.toString()
        val chipNumber = petChipNumb.text.toString()
        val chipDate = petChipDate.text.toString()
        val chipLocation = petChipLoc.text.toString()
        val ownersComment = petOwnersComm.text.toString()
        if (name.isNotEmpty() && dateOfBirth.isNotEmpty() && sex.isNotEmpty() && breed.isNotEmpty() && color.isNotEmpty() && tagNumber.isNotEmpty() && marks.isNotEmpty() && pedigreeNumber.isNotEmpty() && chipNumber.isNotEmpty() && chipDate.isNotEmpty() && chipLocation.isNotEmpty() && ownersComment.isNotEmpty()) {
            val petsInfo = PetsInfo(pathToPicture, name, dateOfBirth, sex, breed, color, tagNumber, marks, pedigreeNumber, chipNumber, chipDate, chipLocation, ownersComment)
            petsInfoDao.add(petsInfo)
            if (!switch.isActivated){
            startActivity(Intent(this, MainActivity::class.java))}
//            else {startActivity(Intent(this, PetsInfoAdd::class.java))}
        }
        else {
            Toast.makeText(this, "Fields can't be empty", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.extras?.get("data")?.run {
            pathToPicture = saveImage(this as Bitmap, petPhoto, petsPictureDirectory)
            photoWasLoaded = true
            noPetPhoto.visibility = View.INVISIBLE
        }
    }

    private fun createDirectoryForOwnerPicture() {
        createDirectory(applicationContext)?.run {
            petsPictureDirectory = this
        }
    }
}