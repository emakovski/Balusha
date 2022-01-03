package com.egor.balusha.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.egor.balusha.R
import com.egor.balusha.activities.main.view.MainActivity
import com.egor.balusha.createOwnersDirectory
import com.egor.balusha.databinding.OwnersBioEditBinding
import com.egor.balusha.saveImage
import com.egor.balusha.util.BalushaApplication
import com.egor.balusha.util.Prefs
import java.io.File

private const val REQUEST_CODE_OWNER_PHOTO = 2
private const val IMAGE_CHOOSE = 1000
private const val PERMISSION_CODE = 1001


class OwnerInfoEdit : AppCompatActivity() {

    private lateinit var binding: OwnersBioEditBinding
    private val prefs: Prefs = BalushaApplication.prefs!!

    private lateinit var ownerPictureDirectory: File
    private lateinit var pathToPicture: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OwnersBioEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        createDirectoryForOwnerPicture()
        setListeners()
    }

    private fun initView(){
        with(binding){
            if (prefs.ownerPhoto.isNullOrBlank()) {
                photoOfOwnerInBioEdit.setImageResource(R.drawable.no_photo)
            } else Glide.with(applicationContext).load(prefs.ownerPhoto).into(photoOfOwnerInBioEdit)
            nameOfOwnerInBioEdit.setText(prefs.ownerName)
            surnameOfOwnerInBioEdit.setText(prefs.ownerSurname)
            countryInBioEdit.setText(prefs.ownerCountry)
            cityInBioEdit.setText(prefs.ownerCity)
            streetInBioEdit.setText(prefs.ownerStreet)
            houseInBioEdit.setText(prefs.ownerHouse)
            buildingInBioEdit.setText(prefs.ownerBuilding)
            aptInBioEdit.setText(prefs.ownerApartment)
            phoneNumberInBioEdit.setText(prefs.ownerPhone)
            emailInBioEdit.setText(prefs.ownerEmail)
        }
    }

    private fun setListeners() {
        binding.photoOfOwnerInBioEdit.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(R.string.choose_source)
                .setPositiveButton(R.string.camera){_,_->
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent, REQUEST_CODE_OWNER_PHOTO)
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
        binding.fabInOwnersBioEdit.setOnClickListener {
            editOwnerInfoAndGoToNextActivity()
        }
        binding.buttonBack.setOnClickListener {
            startActivity(Intent(applicationContext, OwnerBio::class.java))
        }
    }

    private fun editOwnerInfoAndGoToNextActivity() {
        with(binding) {
            if (nameOfOwnerInBioEdit.text.toString().isBlank()&&surnameOfOwnerInBioEdit.text.toString().isBlank()) {
                Toast.makeText(applicationContext, R.string.fill_name_alert, Toast.LENGTH_SHORT).show()
            } else {
                prefs.ownerPhoto = pathToPicture
                prefs.ownerName = nameOfOwnerInBioEdit.text.toString()
                prefs.ownerSurname = surnameOfOwnerInBioEdit.text.toString()
                prefs.ownerCountry = countryInBioEdit.text.toString()
                prefs.ownerCity = cityInBioEdit.text.toString()
                prefs.ownerStreet = streetInBioEdit.text.toString()
                prefs.ownerHouse = houseInBioEdit.text.toString()
                prefs.ownerBuilding = buildingInBioEdit.text.toString()
                prefs.ownerApartment = aptInBioEdit.text.toString()
                prefs.ownerPhone = phoneNumberInBioEdit.text.toString()
                prefs.ownerEmail = emailInBioEdit.text.toString()
                startActivity(Intent(applicationContext, OwnerBio::class.java))
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
                binding.photoOfOwnerInBioEdit.setImageURI(data?.data)
                pathToPicture = data?.data.toString()
            }
            REQUEST_CODE_OWNER_PHOTO -> {
                data?.extras?.get("data")?.run {
                    pathToPicture =
                        saveImage(this as Bitmap, binding.photoOfOwnerInBioEdit, ownerPictureDirectory)
                }
            }
        }
    }

    private fun createDirectoryForOwnerPicture() {
        createOwnersDirectory(applicationContext)?.run {
            ownerPictureDirectory = this
        }
    }
}