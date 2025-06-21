package com.example.mvc_yongho.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.mvc_yongho.R

object ViewBindingAdapter {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(imageView: ImageView, imageUrl: String?) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .error(R.drawable.no_image)
            .fallback(R.drawable.no_image)
            .into(imageView)
    }
}
