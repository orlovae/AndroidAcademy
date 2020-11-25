package ru.aleksandrorlove.appname

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val fragmentMoviesDetails = FragmentMoviesDetails()
    private val fragmentMoviesList = FragmentMoviesList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val fragmentTransition = fragmentManager.beginTransaction()

        fragmentTransition.apply {
            add(R.id.fragment_container_view, fragmentMoviesDetails)
            add(R.id.fragment_container_view, fragmentMoviesList)
            commit()
        }
    }
}