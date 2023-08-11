package com.example.hilt.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.hilt.R
import com.example.hilt.data.model.Settings
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var settings = Settings()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }





    fun settingsActivity(view: View) {
        Intent(this, SettingsActivity::class.java).also {
            it.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(it)
        }
    }

    fun play(view: View) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("settings", settings)
        startActivity(intent)
    }
}
