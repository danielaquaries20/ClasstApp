package com.example.classtapp.ui.edit_profile

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.isEmptyRequired
import com.crocodic.core.extension.snacked
import com.crocodic.core.extension.textOf
import com.crocodic.core.helper.DateTimeHelper
import com.example.classtapp.R
import com.example.classtapp.base.activity.BaseActivity
import com.example.classtapp.data.constant.Const
import com.example.classtapp.databinding.ActivityEditProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

@AndroidEntryPoint
class EditProfileActivity : BaseActivity<ActivityEditProfileBinding, EditProfileViewModel>() {

    private var filePhoto: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutRes(R.layout.activity_edit_profile)

        binding.data = intent.getParcelableExtra(Const.BUNDLE.SELF)

        viewModel.user.observe(this) { user ->
            binding.data = user
        }

        observe()


    }

    private fun observe() {
        viewModel.apiResponse.observe(this) {
            when (it.status) {
                ApiStatus.LOADING -> {
                    it.message?.let { msg -> loadingDialog.show(msg) }
                }
                ApiStatus.SUCCESS -> {
                    loadingDialog.dismiss()
                    finish()
                }
                ApiStatus.WRONG, ApiStatus.ERROR -> {
                    it.message?.let { msg -> loadingDialog.setResponse(msg) }
                }
            }
        }
    }

    private fun validateForm() {
//        if (binding.etUsername.text?.isEmpty() == true) {
//            binding.etUsername.error = "Form must filled"
//        } else if (binding.etDescription.text?.isEmpty() == true) {
//            binding.etDescription.error = "Form must filled"
//        }


        if (listOf(
                binding.etEditProfileName, binding.etEditProfileDescription
            ).isEmptyRequired(R.string.lbl_required_edit_text)
        ) else {

            viewModel.user.observe(this) {
                loadingDialog.show("Updating...")
                if (filePhoto != null) {
                    filePhoto?.let {
                        viewModel.
                        update(
                            binding.etEditProfileName.textOf(),
                            "SMK Negeri 11 Semarang",
                            binding.etEditProfileDescription.textOf(),
                            it
                        )
                        setResult(7)
                    }
                } else {
                    viewModel.updateNoImage(
                        binding.etEditProfileName.textOf(),
                        "SMK Negeri 11 Semarang",
                        binding.etEditProfileDescription.textOf()
                    )
                    setResult(7)
                }

            }

//            viewModel.user.observe(this) {
//                loadingDialog.show("Updating...")
//                filePhoto?.let {
//                    viewModel.update(
//                        binding.etUsername.textOf(),
//                        binding.spinnerSchool.selectedItem as String,
//                        binding.etDescription.textOf(),
//                        it
//                    )
//                }
//            }
        }

//        loadingDialog.show("Updating...")
//        viewModel.update(
//            binding.etUsername.textOf(),
//            binding.spinnerSchool.selectedItem as String,
//            binding.etDescription.textOf(),
//            null
//        )
    }


    private var activityLauncherGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            result.data?.data?.let {
                generateFileImage(it)
            }
        }

    private fun checkPermissionGallery(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activityLauncherGallery.launch(galleryIntent)
    }

    private fun requestPermissionGallery() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
            110
        )
    }

    private fun generateFileImage(uri: Uri) {
        try {
            val parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r")
            val fileDescriptor = parcelFileDescriptor?.fileDescriptor
            val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor?.close()

            val orientation = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                getOrientation2(uri)
            } else {
                getOrientation(uri)
            }

            val file = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                createImageFile()
            } else {
                //File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}" + File.separator + "BurgerBangor", getNewFileName())
                File(externalCacheDir?.absolutePath, getNewFileName())
            }

            val fos = FileOutputStream(file)
            var bitmap = image

            if (orientation != -1 && orientation != 0) {

                val matrix = Matrix()
                when (orientation) {
                    6 -> matrix.postRotate(90f)
                    3 -> matrix.postRotate(180f)
                    8 -> matrix.postRotate(270f)
                    else -> matrix.postRotate(orientation.toFloat())
                }
                bitmap =
                    Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
            }

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            binding.ivEditProfilePhoto.setImageBitmap(bitmap)
            filePhoto = file
        } catch (e: Exception) {
            e.printStackTrace()
            binding.root.snacked("File ini tidak dapat digunakan")
        }
    }

    @SuppressLint("Range")
    private fun getOrientation(shareUri: Uri): Int {
        val orientationColumn = arrayOf(MediaStore.Images.Media.ORIENTATION)
        val cur = contentResolver.query(
            shareUri,
            orientationColumn,
            null,
            null,
            null
        )
        var orientation = -1
        if (cur != null && cur.moveToFirst()) {
            if (cur.columnCount > 0) {
                orientation = cur.getInt(cur.getColumnIndex(orientationColumn[0]))
            }
            cur.close()
        }
        return orientation
    }

    @SuppressLint("NewApi")
    private fun getOrientation2(shareUri: Uri): Int {
        val inputStream = contentResolver.openInputStream(shareUri)
        return getOrientation3(inputStream)
    }

    @SuppressLint("NewApi")
    private fun getOrientation3(inputStream: InputStream?): Int {
        val exif: ExifInterface
        var orientation = -1
        inputStream?.let {
            try {
                exif = ExifInterface(it)
                orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return orientation
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = DateTimeHelper().createAtLong().toString()
        val storageDir =
            getAppSpecificAlbumStorageDir(Environment.DIRECTORY_DOCUMENTS, "Attachment")
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )
    }

    private fun getNewFileName(isPdf: Boolean = false): String {
        val timeStamp = DateTimeHelper().createAtLong().toString()
        return if (isPdf) "PDF_${timeStamp}_.pdf" else "JPEG_${timeStamp}_.jpg"
    }

    private fun getAppSpecificAlbumStorageDir(albumName: String, subAlbumName: String): File {
        // Get the pictures directory that's inside the app-specific directory on
        // external storage.
        val file = File(getExternalFilesDir(albumName), subAlbumName)
        if (!file.mkdirs()) {
            //Log.e("fssfsf", "Directory not created")
        }
        return file
    }


    fun onClickEditProfileActivity(v: View?) {
        when (v) {
            binding.ivEditProfileClose -> onBackPressed()
            binding.ivEditProfileUpdate -> validateForm()
            binding.ivEditProfilePhoto -> {
                if (checkPermissionGallery()) {
                    openGallery()
                } else {
                    requestPermissionGallery()
                }
            }
            binding.tvEditProfileChangePhoto -> {
                if (checkPermissionGallery()) {
                    openGallery()
                } else {
                    requestPermissionGallery()
                }
            }


        }

        super.onClick(v)
    }

}