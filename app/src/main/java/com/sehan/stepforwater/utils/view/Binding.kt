package com.sehan.stepforwater.utils.view

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url:String){
    Glide.with(this.context)
        .load(url)
        .into(this)
}