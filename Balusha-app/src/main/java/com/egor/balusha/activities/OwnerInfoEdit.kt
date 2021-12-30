package com.egor.balusha.activities

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
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
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_CODE_OWNER_PHOTO)
        }
        binding.fabInOwnersBioEdit.setOnClickListener {
            editOwnerInfoAndGoToNextActivity()
        }
        binding.buttonBackToMenuOwnersBioEdit.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun editOwnerInfoAndGoToNextActivity() {
        with(binding) {
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
            if (prefs.ownerName.isNullOrBlank()&&prefs.ownerSurname.isNullOrBlank()) {
                Toast.makeText(applicationContext, R.string.fill_name_alert, Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(applicationContext, OwnerBio::class.java))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.extras?.get("data")?.run {
            pathToPicture = saveImage(this as Bitmap, binding.photoOfOwnerInBioEdit, ownerPictureDirectory)
        }
    }

    private fun createDirectoryForOwnerPicture() {
//        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
//            ownerPictureDirectory = File("${getExternalFilesDir(Environment.DIRECTORY_PICTURES)}/OwnersPicture")
//            if (!ownerPictureDirectory.exists()) {
//                ownerPictureDirectory.mkdir()
//            }
//        }
        createOwnersDirectory(applicationContext)?.run {
            ownerPictureDirectory = this
        }
    }
}