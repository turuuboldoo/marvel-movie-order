package mn.turbo.marvel.common

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import mn.turbo.marvel.R

private val factory = DrawableCrossFadeFactory
    .Builder()
    .setCrossFadeEnabled(true)
    .build()

private fun getPlaceholder(): RequestOptions {
    return RequestOptions()
        .transform(RoundedCorners(20))
        .placeholder(R.drawable.ic_placeholder)
        .error(R.drawable.ic_placeholder)
        .diskCacheStrategy(DiskCacheStrategy.DATA)
}

fun ImageView.setImageUrl(url: String?) {
    Glide.with(this)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade(factory))
        .apply(getPlaceholder())
        .into(this)
}