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
import com.bumptech.glide.Glide
import com.egor.balusha.R
import com.egor.balusha.activities.main.view.MainActivity
import com.egor.balusha.createPetsDirectory
import com.egor.balusha.databinding.PetsBioEditBinding
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

class PetInfoEdit : AppCompatActivity() {
    private lateinit var binding: PetsBioEditBinding
    private val prefs: Prefs = BalushaApplication.prefs!!

    private lateinit var pathToPicture: String
    private lateinit var petsPictureDirectory: File
    private val cal: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PetsBioEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        createDirectoryForPetsPicture()
        setListeners()
    }

    private fun initView(){
        with(binding) {
            if (prefs.petPhoto.isNullOrBlank()) {
                photoOfDogInBioEdit.setImageResource(R.drawable.no_photo)
            } else Glide.with(applicationContext).load(prefs.petPhoto).into(photoOfDogInBioEdit)

            nameOfDogInBioEdit.setText(prefs.petName)
            dateOfBirthInBioEdit.setText(prefs.petBirth)
            sexInBioEdit.setText(prefs.petSex)
            breedOfDogInBioEdit.setText(prefs.petBreed)
            colorOfDogInBioEdit.setText(prefs.petColor)
            tagNumberInBioEdit.setText(prefs.petTag)
            marksInBioEdit.setText(prefs.petMarks)
            pedigreeNumberInBioEdit.setText(prefs.petPedigree)
            chipNumberInBioEdit.setText(prefs.petChipNumb)
            chippingDateInBioEdit.setText(prefs.petChipDate)
            chipLocationInBioEdit.setText(prefs.petChipLoc)
            commentInBioEdit.setText(prefs.petComment)
        }
    }

    private fun setListeners() {
        binding.buttonBack.setOnClickListener {
            startActivity(Intent(applicationContext, PetBio::class.java))
        }
        binding.fabInPetsBioEdit.setOnClickListener {
            editPetInfoAndBackToPreviousActivity()
        }
        binding.photoOfDogInBioEdit.setOnClickListener {
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
                .setCancelable(false)
                .create()
                .show()
        }
        binding.dateOfBirthInBioEdit.setOnClickListener{
            val datePickerDialog = DatePickerDialog(
                this, { _, year, month, dayOfMonth ->
                    binding.dateOfBirthInBioEdit.setText(DateHumanizer.humanize(dateSelected(year, month, dayOfMonth)))
                }
                , cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
        binding.chippingDateInBioEdit.setOnClickListener{
            val datePickerDialog = DatePickerDialog(
                this, { _, year, month, dayOfMonth ->
                    binding.chippingDateInBioEdit.setText(DateHumanizer.humanize(dateSelected(year, month, dayOfMonth)))
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

    private fun editPetInfoAndBackToPreviousActivity() {
        with(binding) {
            if (nameOfDogInBioEdit.text.toString().isEmpty()&&dateOfBirthInBioEdit.text.toString().isEmpty()&&sexInBioEdit.text.toString().isEmpty()&&breedOfDogInBioEdit.text.toString().isEmpty()) {
                if(nameOfDogInBioEdit.text.toString().isEmpty()){
                    nameOfDogInBioEdit.requestFocus()
                    nameOfDogInBioEdit.background = ContextCompat.getDrawable(applicationContext, R.drawable.recycle_tile_red)
                } else {
                    nameOfDogInBioEdit.background.applyTheme(theme)
                }
                if (dateOfBirthInBioEdit.text.toString().isEmpty()){
                    dateOfBirthInBioEdit.requestFocus()
                    dateOfBirthInBioEdit.background = ContextCompat.getDrawable(applicationContext, R.drawable.recycle_tile_red)
                } else {
                    dateOfBirthInBioEdit.background = ContextCompat.getDrawable(applicationContext, R.drawable.recycle_tile)
                }
                if (sexInBioEdit.text.toString().isEmpty()){
                    sexInBioEdit.requestFocus()
                    sexInBioEdit.background = ContextCompat.getDrawable(applicationContext, R.drawable.recycle_tile_red)
                } else {
                    sexInBioEdit.background = ContextCompat.getDrawable(applicationContext, R.drawable.recycle_tile)
                }
                if (breedOfDogInBioEdit.text.toString().isEmpty()){
                    breedOfDogInBioEdit.requestFocus()
                    breedOfDogInBioEdit.background = ContextCompat.getDrawable(applicationContext, R.drawable.recycle_tile_red)
                } else {
                    breedOfDogInBioEdit.background = ContextCompat.getDrawable(applicationContext, R.drawable.recycle_tile)
                }
                Toast.makeText(applicationContext, R.string.fill_info_alert, Toast.LENGTH_SHORT).show()
            } else {
                prefs.petPhoto = pathToPicture
                prefs.petName = nameOfDogInBioEdit.text.toString()
                prefs.petBirth = dateOfBirthInBioEdit.text.toString()
                prefs.petSex = sexInBioEdit.text.toString()
                prefs.petBreed = breedOfDogInBioEdit.text.toString()
                prefs.petColor = colorOfDogInBioEdit.text.toString()
                prefs.petTag = tagNumberInBioEdit.text.toString()
                prefs.petMarks = marksInBioEdit.text.toString()
                prefs.petPedigree = pedigreeNumberInBioEdit.text.toString()
                prefs.petChipNumb = chipNumberInBioEdit.text.toString()
                prefs.petChipDate = chippingDateInBioEdit.text.toString()
                prefs.petChipLoc = chipLocationInBioEdit.text.toString()
                prefs.petComment = commentInBioEdit.text.toString()
                startActivity(Intent(applicationContext, PetBio::class.java))
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
                binding.photoOfDogInBioEdit.setImageURI(data?.data)
                pathToPicture = data?.data.toString()
            }
            REQUEST_CODE_PET_PHOTO -> {
                data?.extras?.get("data")?.run {
                    pathToPicture =
                        saveImage(this as Bitmap, binding.photoOfDogInBioEdit, petsPictureDirectory)
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