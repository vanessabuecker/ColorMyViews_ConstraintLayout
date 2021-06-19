package com.vbuecker.colormyviews

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.vbuecker.colormyviews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var context: Context = this
    val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0

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

        sharedPreferences = getSharedPreferences("colors", MODE_PRIVATE)
        for (box in boxes) {
            findViewById<View>(box).setBackgroundResource(
                sharedPreferences.getInt(
                    "box-$box",
                    R.color.gray
                )
            )
        }

        val floatingButton = binding.floatingActionButton
        floatingButton.setOnClickListener {
            // TODO: 19/06/2021
        }
    }

    fun onButtonClick(view: View) {
        this.color = when (view) {
            binding.buttonRed -> R.color.red_darker
            binding.buttonGreen -> R.color.green
            binding.buttonYellow -> R.color.yellow
            else -> R.color.gray
        }
    }

    fun onBoxClick(view: View) {
        view.setBackgroundResource(this.color)
        var binding = view
        with(sharedPreferences.edit()) {
            putInt("box-$binding", color)
            commit()
        }
    }

    private fun saveScreenShot(bitmap: Bitmap, fileName: String) {
    }
}

private fun takeScreenShot(view: View) {
    // TODO: 19/06/2021
}