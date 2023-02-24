package com.example.slotmachine

import android.app.Activity
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.slotmachine.databinding.ActivityGameBinding
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private lateinit var extra: String
    private lateinit var message: String
    private lateinit var images: IntArray
    private val btnDraw by lazy { binding.btnDraw }
    private val txtDisplay by lazy { binding.tvDisplay }
    private val imageViews by lazy {
        listOf(binding.imageView1, binding.imageView2, binding.imageView3)
    }
    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        extra = intent.getStringExtra(HEADER) ?: getString(R.string.show_apple)

        /**
         * This is used to saved the UI state when the screen rotates or user
         * navigates out of the app and comes back, we use the elvis operator( ?: ) in case
         * these calls getString/getIntArray return null. If they do return null display
         * that default value, in this case "Show that APPLE" string and a array of
         * the empty image
         */
        message = savedInstanceState?.getString(MESSAGE) ?: getString(R.string.show_apple)
        images = savedInstanceState?.getIntArray(IMAGES)
            ?: intArrayOf(R.drawable.empty, R.drawable.empty, R.drawable.empty)

        displayRandomImage()
        displayMessage()

        btnDraw.setOnClickListener { drawSlotMachine() }
    }

    override fun finish() {
        val data = Intent()
        data.putExtra(RETURN_KEY, getString(R.string.thank_you))
        setResult(Activity.RESULT_OK, data)
        super.finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_game, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.itemShare) {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Lucky Apple Slot Machine:" +
                        "\nYou draw: ${getAppleCount().toString()} apple(s) " +
                        "\non ${Calendar.getInstance().time}")
                type = "text/plain"
            }
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
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
            else -> message = extra
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