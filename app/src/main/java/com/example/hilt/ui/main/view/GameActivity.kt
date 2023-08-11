package com.example.hilt.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hilt.R
import com.example.hilt.data.model.Settings
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameActivity : AppCompatActivity() {

    lateinit var settings: Settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settings = intent.getSerializableExtra("settings") as Settings
        setContentView(R.layout.activity_game)
    }

    override fun onRestart() {
        super.onRestart()
        this.onDestroy()
        this.onCreate(null)
    }
}