package com.example.slotmachine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.example.slotmachine.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var message: String
    private lateinit var images: IntArray
    private val btnDraw by lazy { binding.btnDraw }
    private val txtDisplay by lazy { binding.tvDisplay }
    private val imageViews by lazy {
        listOf(binding.imageView1, binding.imageView2, binding.imageView3)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * This is used to saved the UI state when the screen rotates or user
         * navigates out of the app and comes back, we use the elvis operator( ?: ) in case
         * these calls getString/getIntArray return null. If they do return null display
         * that default value, in this case "Show that APPLE" string and a array of
         * the empty image
         */
        message = savedInstanceState?.getString(MESSAGE) ?: "Show that APPLE"
        images = savedInstanceState?.getIntArray(IMAGES)
            ?: intArrayOf(R.drawable.empty, R.drawable.empty, R.drawable.empty)

        displayRandomImage()
        displayMessage()

        btnDraw.setOnClickListener { drawSlotMachine() }
        Log.i(SHOW_IMAGE_TAG, "onCreate Starts!")
    }

    /**
     * This is used to saved the UI state when the screen rotates or user
     * navigates out of the app and comes back
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(MESSAGE, message)
        outState.putIntArray(IMAGES, images)
        super.onSaveInstanceState(outState)
    }

    private fun displayMessage() {
        when(getAppleCount()) {
            1 -> message = "Nice one..."
            2 -> message = "Good for two!"
            3 -> message = "Lucky three!!!"
            else -> message = "Show that APPLE"
        }
        txtDisplay.text = message
    }

    private fun getAppleCount(): Int {
        var appleCount = 0
        for (i in images) {
            if (R.drawable.apple == i) appleCount++
        }
        return appleCount
    }

    private fun randomizeImage(): Int {
        return when(Random.nextInt(3)) {
            0 -> R.drawable.apple
            1 -> R.drawable.grape
            2 -> R.drawable.orange
            else -> R.drawable.empty
        }
    }

    private fun setRandomImages() {
        images = intArrayOf(randomizeImage(), randomizeImage(), randomizeImage())
    }

    private fun drawSlotMachine() {
        setRandomImages()
        displayRandomImage()
        displayMessage()
    }

    private fun displayRandomImage() {
        for (i in 0 until images.size) {
            imageViews[i].setImageResource(images[i])
        }
    }

}