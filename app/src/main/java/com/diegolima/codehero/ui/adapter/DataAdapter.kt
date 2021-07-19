package com.diegolima.codehero.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.diegolima.codehero.R
import com.diegolima.codehero.constants.CODE_NOT_IMAGE
import com.diegolima.codehero.constants.IMAGE_DEFAULT_ERROR
import com.diegolima.codehero.constants.REPRESENTED_SQUARE
import com.diegolima.codehero.data.network.MarvelApi.model.Character
import com.diegolima.codehero.data.network.loadthumbnail.load
import kotlinx.android.synthetic.main.row_character.view.*

class DataAdapter () : PagedListAdapter<Character,
        DataAdapter.ViewHolder>(characterDiff) {

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(listener : onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_character, parent, false)
        return ViewHolder(view, mListener)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val character = getItem(position)
        viewHolder.textView.text = character?.name

        val path = character?.thumbnail?.path
        val extension = character?.thumbnail?.extension

        var imageCharacter = "$path$REPRESENTED_SQUARE$extension"

        if(path!!.contains(CODE_NOT_IMAGE)){
            imageCharacter = IMAGE_DEFAULT_ERROR
            viewHolder.imageView.load(imageCharacter)
        }else{
            viewHolder.imageView.load(imageCharacter)
        }
    }

    inner class ViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.image_view
        val textView: TextView = itemView.text_view

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    companion object {

        val characterDiff = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem == newItem
            }

        }
    }
}