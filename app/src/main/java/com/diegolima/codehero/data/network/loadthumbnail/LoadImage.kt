package com.diegolima.codehero.data.network.loadthumbnail

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.diegolima.codehero.R

fun ImageView.load(url: String) {
    Glide.with(context)
        .load(url)
        .circleCrop()
        .error(R.drawable.marvel_error)
        .into(this)
}