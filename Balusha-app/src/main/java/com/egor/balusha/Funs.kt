package com.egor.balusha

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.widget.ImageView
import java.io.File
import java.io.FileOutputStream

fun saveImage(photo: Bitmap, iv: ImageView, pictureDirectory: File): String {
    val path = "photo_${System.currentTimeMillis()}.jpg"
    val pathToPicture = "${pictureDirectory.path}/${path}"
    val file = File(pictureDirectory, path)
    file.createNewFile()
    val stream = FileOutputStream(file)
    photo.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    iv.setImageBitmap(photo)
    stream.flush()
    stream.close()
    return pathToPicture
}
fun createOwnersDirectory(context: Context): File? {
    if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
        val pictureDirectory = File("${context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)}/OwnersPicture")
        if (!pictureDirectory.exists()) {
            pictureDirectory.mkdir()
        }
        return pictureDirectory
    }
    return null
}
fun createPetsDirectory(context: Context): File? {
    if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
        val pictureDirectory = File("${context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)}/PetsPicture")
        if (!pictureDirectory.exists()) {
            pictureDirectory.mkdir()
        }
        return pictureDirectory
    }
    return null
}