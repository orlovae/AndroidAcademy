package ru.aleksandrorlove.appname

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), CellClickListener {

    private val fragmentMoviesList = FragmentMoviesList()
    private var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            fragment = supportFragmentManager.getFragment(savedInstanceState, "fragmentTag1")
        } else {
            val fragmentManager = supportFragmentManager
            val fragmentTransition = fragmentManager.beginTransaction()

            fragmentTransition.apply {
                add(R.id.fragment_container_view, fragmentMoviesList)
                commit()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        fragment?.let {
            supportFragmentManager.putFragment(outState, "fragmentTag1", it)

        }
    }

    override fun onCellClickListener(id: Int) {
        (fragment as FragmentMoviesDetails).arguments = bundleOf("id" to id)
    }
}