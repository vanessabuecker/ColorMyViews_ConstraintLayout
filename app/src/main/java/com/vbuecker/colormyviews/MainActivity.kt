package com.vbuecker.colormyviews

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vbuecker.colormyviews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val buttonGreen = binding.buttonGreen

        buttonGreen.setOnClickListener {
            binding.boxOne.setBackgroundColor(Color.GREEN)
        }

        binding.buttonRed.setOnClickListener {
            binding.boxFourText.setBackgroundColor(Color.RED)
        }

        binding.buttonYellow.setOnClickListener {
            binding.boxTwoText.setBackgroundColor(Color.YELLOW)
            binding.boxTwoText.setTextColor(Color.BLACK)
        }
    }

}