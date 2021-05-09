package com.egor.balusha.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.egor.balusha.R
import com.egor.balusha.databinding.PetsBioBinding
import com.egor.balusha.dbpets.DatabasePetsInfo
import com.egor.balusha.dbpets.PetsInfo
import com.egor.balusha.dbpets.PetsInfoDao

private const val PET_INFO_EDIT_ACTIVITY_CODE = 1
private const val RESULT_CODE_BUTTON_BACK = 3

class PetBio : AppCompatActivity() {

    private lateinit var database: DatabasePetsInfo
    private lateinit var petsInfoDAO: PetsInfoDao
    private lateinit var binding: PetsBioBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PetsBioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = DatabasePetsInfo.getDataBase(applicationContext)
        petsInfoDAO = database.getPetsInfoDao()
        dataChange()
        setListener()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_CODE_BUTTON_BACK) {
            dataChange()
        }
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
                val parcel = PetsInfo(petsInfoDAO.getPathToPic(),
                    petsInfoDAO.getName(),
                    petsInfoDAO.getDateOfBirth(),
                    petsInfoDAO.getSex(),
                    petsInfoDAO.getBreed(),
                    petsInfoDAO.getColor(),
                    petsInfoDAO.getTagNumb(),
                    petsInfoDAO.getMarks(),
                    petsInfoDAO.getPedigree(),
                    petsInfoDAO.getChipNumb(),
                    petsInfoDAO.getChipDate(),
                    petsInfoDAO.getChipLoc(),
                    petsInfoDAO.getComment()
                )
                intent.putExtra("petInfo", parcel)
                startActivityForResult(intent, PET_INFO_EDIT_ACTIVITY_CODE)
        }
    }
    private fun backToPreviousActivity() {
        setResult(RESULT_CODE_BUTTON_BACK, intent)
        finish()
    }
    private fun dataChange(){
        if (petsInfoDAO.getPathToPic().isEmpty()) binding.photoOfDogInBio.setImageResource(
            R.drawable.no_photo
        )
        else Glide.with(this).load(petsInfoDAO.getPathToPic()).into(binding.photoOfDogInBio)
        binding.nameOfDogInBio.text = petsInfoDAO.getName()
        binding.dateOfBirthInBio.text = petsInfoDAO.getDateOfBirth()
        binding.sexInBio.text = petsInfoDAO.getSex()
        binding.breedOfDogInBio.text = petsInfoDAO.getBreed()
        binding.colorOfDogInBio.text = petsInfoDAO.getColor()
        binding.tagNumberInBio.text = petsInfoDAO.getTagNumb()
        binding.marksInBio.text = petsInfoDAO.getMarks()
        binding.pedigreeNumberInBio.text = petsInfoDAO.getPedigree()
        binding.chipNumberInBio.text = petsInfoDAO.getChipNumb()
        binding.chippingDateInBio.text = petsInfoDAO.getChipDate()
        binding.chipLocationInBio.text = petsInfoDAO.getChipLoc()
        binding.commentInBio.text = petsInfoDAO.getComment()
    }
}