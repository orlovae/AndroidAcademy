package ru.aleksandrorlove.appname

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button : Button = findViewById(R.id.button)

        button.setOnClickListener {moveToMovieScreen()}

    }

    private fun moveToMovieScreen() {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        startActivity(intent)
    }
}