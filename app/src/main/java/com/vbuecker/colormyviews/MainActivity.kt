package com.vbuecker.colormyviews

import android.content.ContentValues
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vbuecker.colormyviews.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

open class MainActivity : AppCompatActivity() {
    var color: Int = R.color.gray
    var boxes = arrayOf(
        R.id.box_one_text,
        R.id.box_two_text,
        R.id.box_three_text,
        R.id.box_four_text,
        R.id.box_five_text
    )
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val container = binding.container

        sharedPreferences = getSharedPreferences("colors", MODE_PRIVATE)
        for (box in boxes) {
            findViewById<View>(box).setBackgroundResource(
                sharedPreferences.getInt(
                    "box-$box",
                    R.color.gray
                )
            )
        }

        val floatingButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        floatingButton.setOnClickListener {
            val bitmap = getScreenShot(container)
            if (bitmap != null) {
                saveScreenshot(bitmap)
            }
        }
    }

    private fun getScreenShot(v: View): Bitmap? {
        var screenshot: Bitmap? = null

        try {
            screenshot =
                Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(screenshot)
            v.draw(canvas)
        } catch (e: Exception) {
            Log.e("GFG", "Failed to capture screenshot. Error: "+ e.message)
        }
        return screenshot
    }

    private fun saveScreenshot(bitmap: Bitmap) {
        val fileName = "${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                val imgUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imgUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imgDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imgDir, fileName)
            fos = FileOutputStream(image)
        }

        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(this, R.string.image_captured, Toast.LENGTH_SHORT).show()
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_TEXT, R.string.checkout_screenshot)
            shareIntent.type = "image/*"
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(Intent.createChooser(shareIntent, R.string.share_via.toString()))
        }
    }

    fun onButtonClick(view: View) {
        this.color = when (view.id) {
            R.id.button_red -> R.color.red_darker
            R.id.button_green -> R.color.green
            R.id.button_yellow -> R.color.yellow
            else -> R.color.gray
        }
    }

    fun onBoxClick(view: View) {
        view.setBackgroundResource(this.color)
        var id = view.id
        with(sharedPreferences.edit()) {
            putInt("box-$id", color)
            commit()
        }
    }

}
