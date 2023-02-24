package com.example.slotmachine

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.slotmachine.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * This is how navigation between two activities is done
         * in this case we use a button, when it is clicked we use an Intent
         * to navigate to the GameActivity*/
        binding.btnStart.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra(HEADER, getString(R.string.press_draw))
            // startActivityForResult shows as deprecated, my min API level is set to 24
            // it should still work??
            startActivityForResult(intent, REQUEST_CODE)
            //startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            val returnValue = data?.getStringExtra(RETURN_KEY) ?: "Welcome Player"
            binding.tvMain.text = returnValue
        }
    }
}