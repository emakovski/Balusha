package com.egor.balusha.activities

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import java.util.Calendar
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.egor.balusha.createPetsDirectory
import com.egor.balusha.databinding.PetsBioAddBinding
import com.egor.balusha.saveImage
import java.io.File

private const val REQUEST_CODE_PET_PHOTO = 1
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

class PetInfoAdd : AppCompatActivity(){
    private lateinit var binding: PetsBioAddBinding
    private var photoWasLoaded: Boolean = false
    private lateinit var petsPictureDirectory: File
    private lateinit var pathToPicture: String
    private val cal: Calendar = Calendar.getInstance()
    private var picker: DatePickerDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PetsBioAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createDirectoryForPetsPicture()
        setListeners()
    }

    private fun setListeners() {
        binding.photoOfDogInBioAdd.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_CODE_PET_PHOTO)
        }
        binding.fabInPetsBioAdd.setOnClickListener {
            addPetInfoAndGoToNextActivity()
        }
        binding.dateOfBirthInBioAdd.setOnClickListener{
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val month = cal.get(Calendar.MONTH)
            val yearr = cal.get(Calendar.YEAR)
            picker = DatePickerDialog(this,
                { view, year, monthOfYear, dayOfMonth -> binding.dateOfBirthInBioAdd.setText(dayOfMonth.toString() + "." + (monthOfYear + 1) + "." + year) }, yearr, month, day)
            picker!!.show()
        }
        binding.chippingDateInBioAdd.setOnClickListener{
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val month = cal.get(Calendar.MONTH)
            val yearr = cal.get(Calendar.YEAR)
            picker = DatePickerDialog(this,
                { view, year, monthOfYear, dayOfMonth -> binding.chippingDateInBioAdd.setText(dayOfMonth.toString() + "." + (monthOfYear + 1) + "." + year) }, yearr, month, day)
            picker!!.show()
        }
    }

    private fun addPetInfoAndGoToNextActivity() {
        if (!photoWasLoaded) {
            pathToPicture = ""
        }
        val name = binding.nameOfDogInBioAdd.text.toString()
        val dateOfBirth = binding.dateOfBirthInBioAdd.text.toString()
        val sex = binding.sexInBioAdd.text.toString()
        val breed = binding.breedOfDogInBioAdd.text.toString()
        val color = binding.colorOfDogInBioAdd.text.toString()
        val tagNumber = binding.tagNumberInBioAdd.text.toString()
        val marks = binding.marksInBioAdd.text.toString()
        val pedigreeNumber = binding.pedigreeNumberInBioAdd.text.toString()
        val chipNumber = binding.chipNumberInBioAdd.text.toString()
        val chipDate = binding.chippingDateInBioAdd.text.toString()
        val chipLocation = binding.chipLocationInBioAdd.text.toString()
        val ownersComment = binding.commentInBioAdd.text.toString()
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
            startActivity(Intent(this, MainActivity::class.java))}
        else {
            Toast.makeText(this, "Fields can't be empty", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.extras?.get("data")?.run {
            pathToPicture = saveImage(
                this as Bitmap,
                binding.photoOfDogInBioAdd,
                petsPictureDirectory
            )
            photoWasLoaded = true
            binding.noPhotoPetsAdd.visibility = View.INVISIBLE
        }
    }

    private fun createDirectoryForPetsPicture() {
        createPetsDirectory(applicationContext)?.run {
            petsPictureDirectory = this
        }
    }
}