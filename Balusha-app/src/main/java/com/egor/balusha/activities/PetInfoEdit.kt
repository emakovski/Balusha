package com.egor.balusha.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.egor.balusha.R
import com.egor.balusha.databinding.PetsBioEditBinding
import com.egor.balusha.dbpets.DatabasePetsInfo
import com.egor.balusha.dbpets.PetsInfo
import com.egor.balusha.dbpets.PetsInfoDao
import java.io.File

private const val REQUEST_CODE_PET_PHOTO = 1
private const val RESULT_CODE_BUTTON_BACK = 3

class PetInfoEdit : AppCompatActivity() {
    private lateinit var binding: PetsBioEditBinding
    private var photoWasLoaded: Boolean = false
    private lateinit var pathToPicture: String
    private lateinit var petsPictureDirectory: File
    private lateinit var database: DatabasePetsInfo
    private lateinit var petInfoDao: PetsInfoDao
    private var petId: Long = 0
    private lateinit var currentPetInfo: PetsInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PetsBioEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = DatabasePetsInfo.getDataBase(applicationContext)
        petInfoDao = database.getPetsInfoDao()
        setListeners()
        loadDataFromIntent()
    }

    private fun loadDataFromIntent() {
        val petInfo = intent.getParcelableExtra<PetsInfo>("petInfo")
        println(petInfo)
        if (petInfo != null) {
            currentPetInfo = petInfo
            petId = petInfo.id
            val path = petInfo.pathToPicture
            val file = File(path)
            if (file.exists()) {
                if (path == "") {
                    binding.photoOfDogInBioEdit.setImageResource(R.drawable.no_photo)
                } else {
                    Glide.with(this).load(path).into(binding.photoOfDogInBioEdit)
                    photoWasLoaded = true
                    pathToPicture = path
                }
            }
            binding.nameOfDogInBioEdit.setText(petInfo.name)
            binding.dateOfBirthInBioEdit.setText(petInfo.date_of_birth)
            binding.sexInBioEdit.setText(petInfo.sex)
            binding.breedOfDogInBioEdit.setText(petInfo.breed)
            binding.colorOfDogInBioEdit.setText(petInfo.color)
            binding.tagNumberInBioEdit.setText(petInfo.tag_number)
            binding.marksInBioEdit.setText(petInfo.marks)
            binding.pedigreeNumberInBioEdit.setText(petInfo.pedigree_number)
            binding.chipNumberInBioEdit.setText(petInfo.chip_number)
            binding.chippingDateInBioEdit.setText(petInfo.chip_date)
            binding.chipLocationInBioEdit.setText(petInfo.chip_location)
            binding.commentInBioEdit.setText(petInfo.owners_comment)
        }
    }

    private fun setListeners() {
        binding.backToMenuPetsBioEdit.setOnClickListener {
            backToPreviousActivity()
        }
        binding.fabInPetsBioEdit.setOnClickListener {
            editPetInfoAndBackToPreviousActivity()
        }
        binding.photoOfDogInBioEdit.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_CODE_PET_PHOTO)
        }
    }

    private fun editPetInfoAndBackToPreviousActivity() {
        val name = binding.nameOfDogInBioEdit.text.toString()
        val dateOfBirth = binding.dateOfBirthInBioEdit.text.toString()
        val sex = binding.sexInBioEdit.text.toString()
        val breed = binding.breedOfDogInBioEdit.text.toString()
        val color = binding.colorOfDogInBioEdit.text.toString()
        val tagNumber = binding.tagNumberInBioEdit.text.toString()
        val marks = binding.marksInBioEdit.text.toString()
        val pedigreeNumber = binding.pedigreeNumberInBioEdit.text.toString()
        val chipNumber = binding.chipNumberInBioEdit.text.toString()
        val chipDate = binding.chippingDateInBioEdit.text.toString()
        val chipLocation = binding.chipLocationInBioEdit.text.toString()
        val ownersComment = binding.commentInBioEdit.text.toString()
        if (name.isNotEmpty() && dateOfBirth.isNotEmpty() && sex.isNotEmpty() && breed.isNotEmpty() && color.isNotEmpty() && tagNumber.isNotEmpty() && marks.isNotEmpty() && pedigreeNumber.isNotEmpty() && chipNumber.isNotEmpty() && chipDate.isNotEmpty() && chipLocation.isNotEmpty() && ownersComment.isNotEmpty()) {
            if (!photoWasLoaded) {
                pathToPicture = ""
            }
            val petInfo = PetsInfo(
                pathToPicture,
                name,
                dateOfBirth,
                sex,
                breed,
                color,
                tagNumber,
                marks,
                pedigreeNumber,
                chipNumber,
                chipDate,
                chipLocation,
                ownersComment
            ).also { it.id = petId }
            petInfoDao.update(petInfo)
            setResult(Activity.RESULT_OK)
            finish()
        } else {
            Toast.makeText(this, "Fields can't be empty", Toast.LENGTH_SHORT).show()
        }
    }

    private fun backToPreviousActivity() {
        setResult(RESULT_CODE_BUTTON_BACK, intent)
        finish()
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        data?.extras?.get("data")?.run {
//            pathToPicture =
//                saveImage(this as Bitmap, binding.photoOfDogInBioEdit, petsPictureDirectory)
//            photoWasLoaded = true
//        }
//    }

}