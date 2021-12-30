package com.egor.balusha.util

import android.content.Context
import android.content.SharedPreferences

const val GLOBAL_PREFERENCES = "GLOBAL_PREFERENCES"

class Prefs (context: Context)
{
    private val NIGHT_THEME = "nightTheme"
    private val FIRST_RUN = "isFirstRun"

    private val OWNER_PHOTO = "photo"
    private val OWNER_NAME = "name"
    private val OWNER_SURNAME = "surname"
    private val OWNER_COUNTRY = "country"
    private val OWNER_CITY = "city"
    private val OWNER_STREET = "street"
    private val OWNER_HOUSE = "house"
    private val OWNER_BUILDING = "building"
    private val OWNER_APARTMENT = "apartment"
    private val OWNER_PHONE = "phone"
    private val OWNER_EMAIL = "email"

    private val PET_PHOTO = "pet_photo"
    private val PET_NAME = "pet_name"
    private val PET_BIRTH = "pet_birth"
    private val PET_SEX = "pet_sex"
    private val PET_BREED = "pet_breed"
    private val PET_COLOR = "pet_color"
    private val PET_TAG = "pet_tag"
    private val PET_MARKS = "pet_marks"
    private val PET_PEDIGREE = "pet_pedigree"
    private val PET_CHIP_NUMB = "pet_chip_numb"
    private val PET_CHIP_DATE = "pet_chip_date"
    private val PET_CHIP_LOC = "pet_chip_loc"
    private val PET_COMMENT = "pet_comment"

    private val preferences: SharedPreferences = context.getSharedPreferences(GLOBAL_PREFERENCES, Context.MODE_PRIVATE)

    var nightTheme: String?
        get() = preferences.getString(NIGHT_THEME, "")
        set(value) = preferences.edit().putString(NIGHT_THEME, value).apply()

    var isFirstRun: Boolean
        get() = preferences.getBoolean(FIRST_RUN, true)
        set(value) = preferences.edit().putBoolean(FIRST_RUN, value).apply()

    var ownerPhoto: String?
        get() = preferences.getString(OWNER_PHOTO, "")
        set(value) = preferences.edit().putString(OWNER_PHOTO, value).apply()
    var ownerName: String?
        get() = preferences.getString(OWNER_NAME, "")
        set(value) = preferences.edit().putString(OWNER_NAME, value).apply()
    var ownerSurname: String?
        get() = preferences.getString(OWNER_SURNAME, "")
        set(value) = preferences.edit().putString(OWNER_SURNAME, value).apply()
    var ownerCountry: String?
        get() = preferences.getString(OWNER_COUNTRY, "")
        set(value) = preferences.edit().putString(OWNER_COUNTRY, value).apply()
    var ownerCity: String?
        get() = preferences.getString(OWNER_CITY, "")
        set(value) = preferences.edit().putString(OWNER_CITY, value).apply()
    var ownerStreet: String?
        get() = preferences.getString(OWNER_STREET, "")
        set(value) = preferences.edit().putString(OWNER_STREET, value).apply()
    var ownerHouse: String?
        get() = preferences.getString(OWNER_HOUSE, "")
        set(value) = preferences.edit().putString(OWNER_HOUSE, value).apply()
    var ownerBuilding: String?
        get() = preferences.getString(OWNER_BUILDING, "")
        set(value) = preferences.edit().putString(OWNER_BUILDING, value).apply()
    var ownerApartment: String?
        get() = preferences.getString(OWNER_APARTMENT, "")
        set(value) = preferences.edit().putString(OWNER_APARTMENT, value).apply()
    var ownerPhone: String?
        get() = preferences.getString(OWNER_PHONE, "")
        set(value) = preferences.edit().putString(OWNER_PHONE, value).apply()
    var ownerEmail: String?
        get() = preferences.getString(OWNER_EMAIL, "")
        set(value) = preferences.edit().putString(OWNER_EMAIL, value).apply()

    var petPhoto: String?
        get() = preferences.getString(PET_PHOTO, "")
        set(value) = preferences.edit().putString(PET_PHOTO, value).apply()
    var petName: String?
        get() = preferences.getString(PET_NAME, "")
        set(value) = preferences.edit().putString(PET_NAME, value).apply()
    var petBirth: String?
        get() = preferences.getString(PET_BIRTH, "")
        set(value) = preferences.edit().putString(PET_BIRTH, value).apply()
    var petSex: String?
        get() = preferences.getString(PET_SEX, "")
        set(value) = preferences.edit().putString(PET_SEX, value).apply()
    var petBreed: String?
        get() = preferences.getString(PET_BREED, "")
        set(value) = preferences.edit().putString(PET_BREED, value).apply()
    var petColor: String?
        get() = preferences.getString(PET_COLOR, "")
        set(value) = preferences.edit().putString(PET_COLOR, value).apply()
    var petTag: String?
        get() = preferences.getString(PET_TAG, "")
        set(value) = preferences.edit().putString(PET_TAG, value).apply()
    var petMarks: String?
        get() = preferences.getString(PET_MARKS, "")
        set(value) = preferences.edit().putString(PET_MARKS, value).apply()
    var petPedigree: String?
        get() = preferences.getString(PET_PEDIGREE, "")
        set(value) = preferences.edit().putString(PET_PEDIGREE, value).apply()
    var petChipNumb: String?
        get() = preferences.getString(PET_CHIP_NUMB, "")
        set(value) = preferences.edit().putString(PET_CHIP_NUMB, value).apply()
    var petChipDate: String?
        get() = preferences.getString(PET_CHIP_DATE, "")
        set(value) = preferences.edit().putString(PET_CHIP_DATE, value).apply()
    var petChipLoc: String?
        get() = preferences.getString(PET_CHIP_LOC, "")
        set(value) = preferences.edit().putString(PET_CHIP_LOC, value).apply()
    var petComment: String?
        get() = preferences.getString(PET_COMMENT, "")
        set(value) = preferences.edit().putString(PET_COMMENT, value).apply()
}