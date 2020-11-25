package ru.aleksandrorlove.appname

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment

class FragmentMoviesList : Fragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_movies_list, container, false)
        val cardView: CardView = view.findViewById(R.id.card_view)
        cardView.setOnClickListener(this)
        return view
    }

    companion object {
        fun newInstance(): FragmentMoviesList {
            return FragmentMoviesList()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.card_view -> {
                Toast.makeText(v.context, "Получилось!!!", Toast.LENGTH_LONG).show()
            }
        }
    }
}