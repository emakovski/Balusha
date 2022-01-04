package com.egor.balusha.activities

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
private const val IMAGE_CHOOSE = 1000
private const val PERMISSION_CODE = 1001

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
            AlertDialog.Builder(this)
                .setTitle(R.string.choose_source)
                .setPositiveButton(R.string.camera){_,_->
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent, REQUEST_CODE_PET_PHOTO)
                }
                .setNeutralButton(R.string.gallery){_,_->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                            requestPermissions(permissions, PERMISSION_CODE)
                        } else{
                            chooseImageGallery()
                        }
                    }else{
                        chooseImageGallery()
                    }
                }
                .setCancelable(true)
                .create()
                .show()
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
            if (nameOfDogInBioAdd.text.toString().isEmpty()&&dateOfBirthInBioAdd.text.toString().isEmpty()&&sexInBioAdd.text.toString().isEmpty()&&breedOfDogInBioAdd.text.toString().isEmpty()) {
                if(nameOfDogInBioAdd.text.toString().isEmpty()){
                    nameOfDogInBioAdd.requestFocus()
                    nameOfDogInBioAdd.background = ContextCompat.getDrawable(applicationContext, R.drawable.recycle_tile_red)
                } else {
                    nameOfDogInBioAdd.background.applyTheme(theme)
                }
                if (dateOfBirthInBioAdd.text.toString().isEmpty()){
                    dateOfBirthInBioAdd.requestFocus()
                    dateOfBirthInBioAdd.background = ContextCompat.getDrawable(applicationContext, R.drawable.recycle_tile_red)
                } else {
                    dateOfBirthInBioAdd.background = ContextCompat.getDrawable(applicationContext, R.drawable.recycle_tile)
                }
                if (sexInBioAdd.text.toString().isEmpty()){
                    sexInBioAdd.requestFocus()
                    sexInBioAdd.background = ContextCompat.getDrawable(applicationContext, R.drawable.recycle_tile_red)
                } else {
                    sexInBioAdd.background = ContextCompat.getDrawable(applicationContext, R.drawable.recycle_tile)
                }
                if (breedOfDogInBioAdd.text.toString().isEmpty()){
                    breedOfDogInBioAdd.requestFocus()
                    breedOfDogInBioAdd.background = ContextCompat.getDrawable(applicationContext, R.drawable.recycle_tile_red)
                } else {
                    breedOfDogInBioAdd.background = ContextCompat.getDrawable(applicationContext, R.drawable.recycle_tile)
                }
                Toast.makeText(applicationContext, R.string.fill_info_alert, Toast.LENGTH_SHORT).show()
            } else {
                if (!photoWasLoaded) {
                    pathToPicture = ""
                }
                prefs.petPhoto = pathToPicture
                prefs.petName = nameOfDogInBioAdd.text.toString()
                prefs.petBirth = dateOfBirthInBioAdd.text.toString()
                prefs.petSex = sexInBioAdd.text.toString()
                prefs.petBreed = breedOfDogInBioAdd.text.toString()
                prefs.petColor = colorOfDogInBioAdd.text.toString()
                prefs.petTag = tagNumberInBioAdd.text.toString()
                prefs.petMarks = marksInBioAdd.text.toString()
                prefs.petPedigree = pedigreeNumberInBioAdd.text.toString()
                prefs.petChipNumb = chipNumberInBioAdd.text.toString()
                prefs.petChipDate = chippingDateInBioAdd.text.toString()
                prefs.petChipLoc = chipLocationInBioAdd.text.toString()
                prefs.petComment = commentInBioAdd.text.toString()
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
        }
    }

    private fun chooseImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_CHOOSE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    chooseImageGallery()
                }else{
                    Toast.makeText(this,"Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode){
            IMAGE_CHOOSE -> {
                binding.photoOfDogInBioAdd.setImageURI(data?.data)
                pathToPicture = data?.data.toString()
            }
            REQUEST_CODE_PET_PHOTO -> {
                data?.extras?.get("data")?.run {
                    pathToPicture =
                        saveImage(this as Bitmap, binding.photoOfDogInBioAdd, petsPictureDirectory)
                }
            }
        }
    }

    private fun createDirectoryForPetsPicture() {
        createPetsDirectory(applicationContext)?.run {
            petsPictureDirectory = this
        }
    }
}