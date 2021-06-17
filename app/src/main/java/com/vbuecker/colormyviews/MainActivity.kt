package com.vbuecker.colormyviews

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vbuecker.colormyviews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Color My Views"

        binding.buttonGreen.setOnClickListener {
            binding.boxOne.setBackgroundColor(Color.GREEN)
            binding.buttonGreen.setBackgroundColor(Color.GRAY)
        }

        binding.buttonRed.setOnClickListener {
            binding.boxFourText.setBackgroundColor(Color.RED)
            binding.buttonRed.setBackgroundColor(Color.GRAY)

        }

        binding.buttonYellow.setOnClickListener {
            binding.boxTwoText.setBackgroundColor(Color.YELLOW)
            binding.boxTwoText.setTextColor(Color.BLACK)
            binding.buttonYellow.setBackgroundColor(Color.GRAY)
        }
    }

}