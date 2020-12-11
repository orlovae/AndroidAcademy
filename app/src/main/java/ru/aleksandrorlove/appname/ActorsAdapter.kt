package ru.aleksandrorlove.appname

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ActorsAdapter(private val actors: List<Actor>) :
    RecyclerView.Adapter<ActorsAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val photo = itemView.findViewById<ImageView>(R.id.holder_actor_photo)
        val name = itemView.findViewById<TextView>(R.id.holder_actor_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val holder = inflater.inflate(R.layout.view_holder_actor, parent, false)

        return ViewHolder(holder)
    }

    override fun onBindViewHolder(holder: ActorsAdapter.ViewHolder, position: Int) {
        val actor: Actor = actors[position]
        val photoImageView = holder.photo
        photoImageView.setImageResource(actor.photo)
        val nameTextView = holder.name
        nameTextView.text = actor.name
    }

    override fun getItemCount(): Int {
        return actors.size
    }
}