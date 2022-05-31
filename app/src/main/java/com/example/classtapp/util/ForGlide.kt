package com.example.classtapp.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.classtapp.R
import de.hdodenhof.circleimageview.CircleImageView

class ForGlide {
    companion object {

        // TODO: 2/22/2022 Tambah Placeholder dan ErrorImage

        @JvmStatic
        @BindingAdapter(value = ["imageUrl"], requireAll = false)
        fun loadImage(view: ImageView, imageUrl: String?){
            view.setImageDrawable(null)

            imageUrl?.let {
                Glide
                    .with(view.context)
                    .load(imageUrl)
                    .apply(RequestOptions.centerCropTransform())
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(view)
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["imageUrlProfile"], requireAll = false)
        fun loadImageProfile(view: CircleImageView, imageUrl: String?){
            view.setImageDrawable(null)

            imageUrl?.let {

                Glide
                    .with(view.context)
                    .load(imageUrl)
                    .apply(RequestOptions.centerCropTransform())
                    .placeholder(R.drawable.upload_photo)
                    .error(R.drawable.error_image)
                    .into(view)
            }
        }


    }

}