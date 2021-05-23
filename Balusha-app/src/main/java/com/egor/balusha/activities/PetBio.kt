package com.egor.balusha.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.egor.balusha.databinding.PetsBioBinding

private const val PET_INFO_EDIT_ACTIVITY_CODE = 1
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

class PetBio : AppCompatActivity() {

    private lateinit var binding: PetsBioBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PetsBioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val prefs: SharedPreferences = getSharedPreferences("pets_info", Context.MODE_PRIVATE)
        Glide.with(this).load(prefs.getString(PET_PHOTO, "no photo")).into(binding.photoOfDogInBio)
        binding.nameOfDogInBio.text=prefs.getString(PET_NAME,"not found")
        binding.dateOfBirthInBio.text=prefs.getString(PET_BIRTH,"not found")
        binding.sexInBio.text=prefs.getString(PET_SEX,"not found")
        binding.breedOfDogInBio.text=prefs.getString(PET_BREED,"not found")
        binding.colorOfDogInBio.text=prefs.getString(PET_COLOR,"not found")
        binding.tagNumberInBio.text=prefs.getString(PET_TAG,"not found")
        binding.marksInBio.text=prefs.getString(PET_MARKS,"not found")
        binding.pedigreeNumberInBio.text=prefs.getString(PET_PEDIGREE,"not found")
        binding.chipNumberInBio.text=prefs.getString(PET_CHIP_NUMB,"not found")
        binding.chippingDateInBio.text=prefs.getString(PET_CHIP_DATE,"not found")
        binding.chipLocationInBio.text=prefs.getString(PET_CHIP_LOC,"not found")
        binding.commentInBio.text=prefs.getString(PET_COMMENT,"not found")
        setListener()
    }
    private fun setListener() {
        binding.fabInBio.setOnClickListener {
            backToPreviousActivity()
        }
        binding.buttonPetsBioOwnerInfo.setOnClickListener {
            startActivity(Intent(this, OwnerBio::class.java))
        }
        binding.buttonPetsBioEdit.setOnClickListener {
            val intent = Intent(this, PetInfoEdit::class.java)
                startActivityForResult(intent, PET_INFO_EDIT_ACTIVITY_CODE)
        }
    }
    private fun backToPreviousActivity() {
        setResult(RESULT_CODE_BUTTON_BACK, intent)
        finish()
    }
}