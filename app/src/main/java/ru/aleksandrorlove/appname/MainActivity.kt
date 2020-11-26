package ru.aleksandrorlove.appname

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    private val fragmentMoviesList = FragmentMoviesList()
    private var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            fragment = supportFragmentManager.getFragment(savedInstanceState, "name")
        } else {
            val fragmentManager = supportFragmentManager
            val fragmentTransition = fragmentManager.beginTransaction()

            fragmentTransition.apply {
                add(R.id.fragment_container_view, fragmentMoviesList)
                commit()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        supportFragmentManager.putFragment(outState, "name", fragment!!)
    }
}