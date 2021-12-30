package com.egor.balusha.activities

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.egor.balusha.R
import com.egor.balusha.createOwnersDirectory
import com.egor.balusha.databinding.OwnersBioAddBinding
import com.egor.balusha.saveImage
import com.egor.balusha.util.BalushaApplication
import com.egor.balusha.util.Prefs
import java.io.File

private const val REQUEST_CODE_OWNER_PHOTO = 2


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
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_CODE_OWNER_PHOTO)
        }
        binding.fabInOwnersBioAdd.setOnClickListener {
            addOwnerInfoAndGoToNextActivity()
        }
    }

    private fun addOwnerInfoAndGoToNextActivity() {
        with(binding) {
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
            if (prefs.ownerName.isNullOrBlank()&&prefs.ownerSurname.isNullOrBlank()) {
                Toast.makeText(applicationContext, R.string.fill_name_alert, Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(applicationContext, PetInfoAdd::class.java))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.extras?.get("data")?.run {
            pathToPicture = saveImage(this as Bitmap, binding.photoOfOwnerInBioAdd, ownerPictureDirectory)
            photoWasLoaded = true
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