package com.example.classtapp.util

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
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
                    .error(R.drawable.error_image_2)
                    .into(view)
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["imageUrlProfile", "initialProfile"], requireAll = false)
        fun loadImageProfile(view: CircleImageView, imageUrl: String?, initialProfile: String?){


            view.post {
                val avatar = StringMasker().generateTextDrawable(
                    StringMasker().getInitial(initialProfile?.trim()),
                    ContextCompat.getColor(view.context, R.color.stated_grey),
                    view.measuredWidth
                )

                if (imageUrl.isNullOrEmpty()) {
                    view.setImageDrawable(avatar)
                } else {
                    val requestOption = RequestOptions().placeholder(avatar).centerCrop()
                    Glide
                        .with(view.context)
                        .load(imageUrl)
                        .apply(requestOption)
                        .error(avatar)
                        .into(view)

                }

            }

        }


    }

}