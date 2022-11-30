package com.network.shopmanager.ui.fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.network.shopmanager.BuildConfig
import com.network.shopmanager.utils.Objects.APP
import kotlinx.coroutines.DelicateCoroutinesApi
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


@DelicateCoroutinesApi
open class FragmentBase : Fragment() {
    private var ivShop: ImageView? = null
    private var uri: Uri? = null
    private var absPath: String = "" // fire Storage ga shu pathdagi file yuklanadi
    var imageBytes: ByteArray? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        super.onResume()

    }

    fun loadImageFromCamera(ivShop: ImageView? = null) {
        this.ivShop = ivShop
        val imageFile = createImageFile()
        uri = FileProvider.getUriForFile(APP, BuildConfig.APPLICATION_ID, imageFile)
        getImageFromCamera.launch(uri)
    }

    fun loadImageFromGallery(ivShop: ImageView? = null) {
        this.ivShop = ivShop
        getImageFromGallery.launch("image/*")
    }

    private val getImageFromGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri ?: return@registerForActivityResult
            val ins = APP.contentResolver?.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(ins)

            absPath = writeToFile(bitmap)
            ivShop?.setImageURI(absPath?.toUri())
        }
    val getImageFromCamera = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            val ins = APP.contentResolver?.openInputStream(uri!!)
            val bitmap = BitmapFactory.decodeStream(ins)
            absPath = writeToFile(bitmap)
            ivShop?.setImageURI(absPath?.toUri())
        }
    }

    private fun createImageFile(): File {
        val timeStamp = System.currentTimeMillis().toString()
        val dir = APP.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", "jpg", dir
        ).apply {
            absPath = absolutePath
        }
    }

    private fun writeToFile(scaledBitmap: Bitmap): String {
        imageBytes = null
        absPath = ""
        val f: File = File(APP.filesDir, "${System.currentTimeMillis()}.jpg");
        f.createNewFile();
        //Convert bitmap to byte array
        val bos: ByteArrayOutputStream = ByteArrayOutputStream();
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 10 /*ignored for PNG*/, bos);
        //write the bytes in file
        val fos: FileOutputStream = FileOutputStream(f)
        val biteArray = bos.toByteArray()
        imageBytes = biteArray
        fos.write(biteArray);
        fos.flush();
        fos.close();
        return f.absolutePath
    }
}