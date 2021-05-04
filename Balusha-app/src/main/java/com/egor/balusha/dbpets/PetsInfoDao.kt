package com.egor.balusha.dbpets

import androidx.room.*

@Dao
interface PetsInfoDao {
    @Query("SELECT * FROM pets_info")
    fun getAll(): List<PetsInfo>

    @Query("SELECT pathToPicture FROM pets_info")
    fun getPathToPic(): String

    @Query("SELECT name FROM pets_info")
    fun getName(): String

    @Query("SELECT date_of_birth FROM pets_info")
    fun getDateOfBirth(): String

    @Query("SELECT sex FROM pets_info")
    fun getSex(): String

    @Query("SELECT breed FROM pets_info")
    fun getBreed(): String

    @Query("SELECT color FROM pets_info")
    fun getColor(): String

    @Query("SELECT tag_number FROM pets_info")
    fun getTagNumb(): String

    @Query("SELECT marks FROM pets_info")
    fun getMarks(): String

    @Query("SELECT pedigree_number FROM pets_info")
    fun getPedigree(): String

    @Query("SELECT chip_number FROM pets_info")
    fun getChipNumb(): String

    @Query("SELECT chip_date FROM pets_info")
    fun getChipDate(): String

    @Query("SELECT chip_location FROM pets_info")
    fun getChipLoc(): String

    @Query("SELECT owners_comment FROM pets_info")
    fun getComment(): String

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(entity: PetsInfo)

    @Update
    fun update(carInfo: PetsInfo)
}