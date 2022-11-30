package mn.turbo.marvel.common

import android.content.Context
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import jp.wasabeef.glide.transformations.CropTransformation
import mn.turbo.marvel.R

@com.bumptech.glide.annotation.GlideModule
class GlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        builder.setLogLevel(Log.WARN)
        builder.setDiskCache(InternalCacheDiskCacheFactory(context))
    }
}

private val factory = DrawableCrossFadeFactory
    .Builder()
    .setCrossFadeEnabled(true)
    .build()

fun ImageView.setImageUrl(url: String?) {
    Glide.with(this)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade(factory))
        .apply(getPlaceholder())
        .into(this)
}

fun ImageView.cropTop(url: String?, width: Int, height: Int) {
    Glide.with(this)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade(factory))
        .apply(getCropPlaceHolder(width, height))
        .into(this)
}

/**
 * xml binding adapter
 **/
@BindingAdapter("imageUrl")
fun imageUrl(imageView: ImageView, url: String?) {
    imageView.setImageUrl(url)
}

/**
 * placeholders
 **/
private fun getPlaceholder(): RequestOptions {
    return RequestOptions()
        .transform(RoundedCorners(20))
        .placeholder(R.drawable.ic_placeholder_grid)
        .error(R.drawable.ic_placeholder_grid)
        .diskCacheStrategy(DiskCacheStrategy.DATA)
}

private fun getCropPlaceHolder(width: Int, height: Int): RequestOptions {
    return RequestOptions()
        .transform(
            CropTransformation(width, height, CropTransformation.CropType.TOP)
        )
        .placeholder(R.drawable.ic_placeholder_detail)
        .error(R.drawable.ic_placeholder_detail)
        .diskCacheStrategy(DiskCacheStrategy.DATA)
}
