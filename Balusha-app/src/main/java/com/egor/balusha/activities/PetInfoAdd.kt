package com.egor.balusha.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.egor.balusha.R
import com.egor.balusha.activities.main.view.MainActivity
import com.egor.balusha.createPetsDirectory
import com.egor.balusha.databinding.PetsBioAddBinding
import com.egor.balusha.lib.DateHumanizer
import com.egor.balusha.receiver.setFiled
import com.egor.balusha.saveImage
import com.egor.balusha.util.BalushaApplication
import com.egor.balusha.util.Prefs
import java.io.File
import java.util.*

private const val REQUEST_CODE_PET_PHOTO = 1

class PetInfoAdd : AppCompatActivity(){
    private lateinit var binding: PetsBioAddBinding
    private val prefs: Prefs = BalushaApplication.prefs!!

    private var photoWasLoaded: Boolean = false
    private lateinit var petsPictureDirectory: File
    private lateinit var pathToPicture: String
    private val cal: Calendar = Calendar.getInstance()


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
            val datePickerDialog = DatePickerDialog(
                this, { _, year, month, dayOfMonth ->
                    binding.dateOfBirthInBioAdd.setText(DateHumanizer.humanize(dateSelected(year, month, dayOfMonth)))
                }
                , cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
        binding.chippingDateInBioAdd.setOnClickListener{
            val datePickerDialog = DatePickerDialog(
                this, { _, year, month, dayOfMonth ->
                    binding.chippingDateInBioAdd.setText(DateHumanizer.humanize(dateSelected(year, month, dayOfMonth)))
                }
                , cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
    }
    private fun dateSelected(year: Int, month: Int, dayOfMonth: Int): Long {
        val cal = Calendar.getInstance()
            .setFiled(Calendar.YEAR, year)
            .setFiled(Calendar.MONTH, month)
            .setFiled(Calendar.DAY_OF_MONTH, dayOfMonth)

        return cal.timeInMillis
    }

    private fun addPetInfoAndGoToNextActivity() {
        with(binding) {
            if (!photoWasLoaded) {
                pathToPicture = ""
            }
            prefs.petPhoto = pathToPicture

            if(nameOfDogInBioAdd.text.toString().isEmpty()){
                nameOfDogInBioAdd.requestFocus()
                nameOfDogInBioAdd.background = ContextCompat.getDrawable(applicationContext, R.drawable.recycle_tile_red)
                Toast.makeText(applicationContext, R.string.fill_info_alert, Toast.LENGTH_SHORT).show()
            } else {
                prefs.petName = nameOfDogInBioAdd.text.toString()
                nameOfDogInBioAdd.background.applyTheme(theme)
            }

            if (dateOfBirthInBioAdd.text.toString().isEmpty()){
                dateOfBirthInBioAdd.requestFocus()
                dateOfBirthInBioAdd.background = ContextCompat.getDrawable(applicationContext, R.drawable.recycle_tile_red)
                Toast.makeText(applicationContext, R.string.fill_info_alert, Toast.LENGTH_SHORT).show()
            } else {
                prefs.petBirth = dateOfBirthInBioAdd.text.toString()
                dateOfBirthInBioAdd.background = ContextCompat.getDrawable(applicationContext, R.drawable.recycle_tile)
            }

            if (sexInBioAdd.text.toString().isEmpty()){
                sexInBioAdd.requestFocus()
                sexInBioAdd.background = ContextCompat.getDrawable(applicationContext, R.drawable.recycle_tile_red)
                Toast.makeText(applicationContext, R.string.fill_info_alert, Toast.LENGTH_SHORT).show()
            } else {
                prefs.petSex = sexInBioAdd.text.toString()
                sexInBioAdd.background = ContextCompat.getDrawable(applicationContext, R.drawable.recycle_tile)
            }

            if (breedOfDogInBioAdd.text.toString().isEmpty()){
                breedOfDogInBioAdd.requestFocus()
                breedOfDogInBioAdd.background = ContextCompat.getDrawable(applicationContext, R.drawable.recycle_tile_red)
                Toast.makeText(applicationContext, R.string.fill_info_alert, Toast.LENGTH_SHORT).show()
            } else {
                prefs.petBreed = breedOfDogInBioAdd.text.toString()
                breedOfDogInBioAdd.background = ContextCompat.getDrawable(applicationContext, R.drawable.recycle_tile)
            }

            prefs.petColor = colorOfDogInBioAdd.text.toString()
            prefs.petTag = tagNumberInBioAdd.text.toString()
            prefs.petMarks = marksInBioAdd.text.toString()
            prefs.petPedigree = pedigreeNumberInBioAdd.text.toString()
            prefs.petChipNumb = chipNumberInBioAdd.text.toString()
            prefs.petChipDate = chippingDateInBioAdd.text.toString()
            prefs.petChipLoc = chipLocationInBioAdd.text.toString()
            prefs.petComment = commentInBioAdd.text.toString()

            if (!prefs.petName.isNullOrBlank()&&!prefs.petBirth.isNullOrBlank()&&!prefs.petSex.isNullOrBlank()&&!prefs.petBreed.isNullOrBlank()) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
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
        }
    }

    private fun createDirectoryForPetsPicture() {
        createPetsDirectory(applicationContext)?.run {
            petsPictureDirectory = this
        }
    }
}