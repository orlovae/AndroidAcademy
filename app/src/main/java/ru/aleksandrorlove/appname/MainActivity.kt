package ru.aleksandrorlove.appname

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import ru.aleksandrorlove.appname.data.Movie

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

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        fragment?.let {
            supportFragmentManager.putFragment(outState, "fragmentTag1", it)
        }
    }

    override fun onCellClickListener(movie: Movie) {
        (fragment as FragmentMoviesDetails).arguments = bundleOf("movie" to movie)
    }
}