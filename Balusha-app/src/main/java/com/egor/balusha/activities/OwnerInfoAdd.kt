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
import com.egor.balusha.R
import com.egor.balusha.createOwnersDirectory
import com.egor.balusha.databinding.OwnersBioAddBinding
import com.egor.balusha.saveImage
import com.egor.balusha.util.BalushaApplication
import com.egor.balusha.util.Prefs
import java.io.File

private const val REQUEST_CODE_OWNER_PHOTO = 2
private const val IMAGE_CHOOSE = 1000
private const val PERMISSION_CODE = 1001


class OwnerInfoAdd : AppCompatActivity(){
    private lateinit var binding: OwnersBioAddBinding
    private val prefs: Prefs = BalushaApplication.prefs!!

    private var photoWasLoaded: Boolean = false
    private lateinit var ownerPictureDirectory: File
    private lateinit var pathToPicture: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OwnersBioAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createDirectoryForOwnerPicture()
        setListeners()
    }

    private fun setListeners() {
        binding.photoOfOwnerInBioAdd.setOnClickListener {
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
                .setCancelable(true)
                .create()
                .show()
        }
        binding.fabInOwnersBioAdd.setOnClickListener {
            addOwnerInfoAndGoToNextActivity()
        }
    }

    private fun addOwnerInfoAndGoToNextActivity() {
        with(binding) {
            if (nameOfOwnerInBioAdd.text.toString().isBlank()&&surnameOfOwnerInBioAdd.text.toString().isBlank()) {
                Toast.makeText(applicationContext, R.string.fill_name_alert, Toast.LENGTH_SHORT).show()
            } else {
                if (!photoWasLoaded) {
                    pathToPicture = ""
                }
                prefs.ownerPhoto = pathToPicture
                prefs.ownerName = nameOfOwnerInBioAdd.text.toString()
                prefs.ownerSurname = surnameOfOwnerInBioAdd.text.toString()
                prefs.ownerCountry = countryInBioAdd.text.toString()
                prefs.ownerCity = cityInBioAdd.text.toString()
                prefs.ownerStreet = streetInBioAdd.text.toString()
                prefs.ownerHouse = houseInBioAdd.text.toString()
                prefs.ownerBuilding = buildingInBioAdd.text.toString()
                prefs.ownerApartment = aptInBioAdd.text.toString()
                prefs.ownerPhone = phoneNumberInBioAdd.text.toString()
                prefs.ownerEmail = emailInBioAdd.text.toString()
                startActivity(Intent(applicationContext, PetInfoAdd::class.java))
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
                binding.photoOfOwnerInBioAdd.setImageURI(data?.data)
                pathToPicture = data?.data.toString()
            }
            REQUEST_CODE_OWNER_PHOTO -> {
                data?.extras?.get("data")?.run {
                    pathToPicture =
                        saveImage(this as Bitmap, binding.photoOfOwnerInBioAdd, ownerPictureDirectory)
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