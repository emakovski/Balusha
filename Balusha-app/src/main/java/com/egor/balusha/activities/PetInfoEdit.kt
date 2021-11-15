package com.egor.balusha.activities

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import java.util.Calendar
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.egor.balusha.R
import com.egor.balusha.activities.main.view.MainActivity
import com.egor.balusha.createPetsDirectory
import com.egor.balusha.databinding.PetsBioEditBinding
import com.egor.balusha.saveImage
import java.io.File

private const val REQUEST_CODE_PET_PHOTO = 1
private const val RESULT_CODE_BUTTON_BACK = 3
private const val PET_PHOTO = "pet_photo"
private const val PET_NAME = "pet_name"
private const val PET_BIRTH = "pet_birth"
private const val PET_SEX = "pet_sex"
private const val PET_BREED = "pet_breed"
private const val PET_COLOR = "pet_color"
private const val PET_TAG = "pet_tag"
private const val PET_MARKS = "pet_marks"
private const val PET_PEDIGREE = "pet_pedigree"
private const val PET_CHIP_NUMB = "pet_chip_numb"
private const val PET_CHIP_DATE = "pet_chip_date"
private const val PET_CHIP_LOC = "pet_chip_loc"
private const val PET_COMMENT = "pet_comment"

class PetInfoEdit : AppCompatActivity() {
    private lateinit var binding: PetsBioEditBinding
    private var photoWasLoaded: Boolean = false
    private lateinit var pathToPicture: String
    private lateinit var petsPictureDirectory: File
    private val cal: Calendar = Calendar.getInstance()
    private var picker: DatePickerDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PetsBioEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val prefs: SharedPreferences = getSharedPreferences("pets_info", Context.MODE_PRIVATE)
        val path = prefs.getString(PET_PHOTO, "no photo")
        val file = File(path)
        if (file.exists()) {
            if (path == "") {
                binding.photoOfDogInBioEdit.setImageResource(R.drawable.no_photo)
            } else {
                Glide.with(this).load(path).into(binding.photoOfDogInBioEdit)
                photoWasLoaded = true
                if (path != null) {
                    pathToPicture = path
                }
            }
        }
        binding.nameOfDogInBioEdit.setText(prefs.getString(PET_NAME,"not found"))
        binding.dateOfBirthInBioEdit.setText(prefs.getString(PET_BIRTH,"not found"))
        binding.sexInBioEdit.setText(prefs.getString(PET_SEX,"not found"))
        binding.breedOfDogInBioEdit.setText(prefs.getString(PET_BREED,"not found"))
        binding.colorOfDogInBioEdit.setText(prefs.getString(PET_COLOR,"not found"))
        binding.tagNumberInBioEdit.setText(prefs.getString(PET_TAG,"not found"))
        binding.marksInBioEdit.setText(prefs.getString(PET_MARKS,"not found"))
        binding.pedigreeNumberInBioEdit.setText(prefs.getString(PET_PEDIGREE,"not found"))
        binding.chipNumberInBioEdit.setText(prefs.getString(PET_CHIP_NUMB,"not found"))
        binding.chippingDateInBioEdit.setText(prefs.getString(PET_CHIP_DATE,"not found"))
        binding.chipLocationInBioEdit.setText(prefs.getString(PET_CHIP_LOC,"not found"))
        binding.commentInBioEdit.setText(prefs.getString(PET_COMMENT,"not found"))
        createDirectoryForPetsPicture()
        setListeners()
    }


    private fun setListeners() {
        binding.backToMenuPetsBioEdit.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.fabInPetsBioEdit.setOnClickListener {
            editPetInfoAndBackToPreviousActivity()
        }
        binding.photoOfDogInBioEdit.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_CODE_PET_PHOTO)
        }
        binding.dateOfBirthInBioEdit.setOnClickListener{
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val month = cal.get(Calendar.MONTH)
            val yearr = cal.get(Calendar.YEAR)
            picker = DatePickerDialog(this,
                { view, year, monthOfYear, dayOfMonth -> binding.dateOfBirthInBioEdit.setText(dayOfMonth.toString() + "." + (monthOfYear + 1) + "." + year) }, yearr, month, day)
            picker!!.show()
        }
        binding.chippingDateInBioEdit.setOnClickListener{
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val month = cal.get(Calendar.MONTH)
            val yearr = cal.get(Calendar.YEAR)
            picker = DatePickerDialog(this,
                { view, year, monthOfYear, dayOfMonth -> binding.chippingDateInBioEdit.setText(dayOfMonth.toString() + "." + (monthOfYear + 1) + "." + year) }, yearr, month, day)
            picker!!.show()
        }
    }

    private fun editPetInfoAndBackToPreviousActivity() {
        if (!photoWasLoaded) {
            pathToPicture = ""
        }
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
            getSharedPreferences("pets_info", Context.MODE_PRIVATE)
                .edit()
                .apply {
                    putString(PET_PHOTO, pathToPicture)
                    putString(PET_NAME, name)
                    putString(PET_BIRTH, dateOfBirth)
                    putString(PET_SEX, sex)
                    putString(PET_BREED, breed)
                    putString(PET_COLOR, color)
                    putString(PET_TAG, tagNumber)
                    putString(PET_MARKS, marks)
                    putString(PET_PEDIGREE, pedigreeNumber)
                    putString(PET_CHIP_NUMB, chipNumber)
                    putString(PET_CHIP_DATE, chipDate)
                    putString(PET_CHIP_LOC, chipLocation)
                    putString(PET_COMMENT, ownersComment)
                }
                .apply()
            startActivity(Intent(this, PetBio::class.java))
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
        data?.extras?.get("data")?.run {
            pathToPicture =
                saveImage(this as Bitmap, binding.photoOfDogInBioEdit, petsPictureDirectory)
            photoWasLoaded = true
        }
    }
    private fun createDirectoryForPetsPicture() {
        createPetsDirectory(applicationContext)?.run {
            petsPictureDirectory = this
        }
    }

}