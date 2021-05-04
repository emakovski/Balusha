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

class PetBio : AppCompatActivity() {

    private lateinit var database: DatabasePetsInfo
    private lateinit var petsInfoDAO: PetsInfoDao
    private lateinit var binding: PetsBioBinding
    private var petInfoList: ArrayList<PetsInfo> = arrayListOf()
//    lateinit var onEditIconClickListener: (petInfo: PetsInfo) -> Unit


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PetsBioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = DatabasePetsInfo.getDataBase(applicationContext)
        petsInfoDAO = database.getPetsInfoDao()

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
        setFabListener()
    }
    private fun setFabListener() {
        binding.fabInBio.setOnClickListener {
            backToPreviousActivity()
        }
        binding.buttonPetsBioOwnerInfo.setOnClickListener {
            startActivity(Intent(this, OwnerBio::class.java))
        }
        binding.buttonPetsBioEdit.setOnClickListener {
            val intent = Intent(this, PetInfoEdit::class.java)
            intent.putExtra("petInfo", petInfoList)
            startActivityForResult(intent, PET_INFO_EDIT_ACTIVITY_CODE)
        }
    }

    private fun backToPreviousActivity() {
        finish()
    }

}