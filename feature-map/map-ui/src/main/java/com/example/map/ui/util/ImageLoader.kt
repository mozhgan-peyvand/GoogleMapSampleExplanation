package com.example.map.ui.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.FutureTarget
import com.bumptech.glide.request.target.CustomTarget
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


fun loadBitmapDescriptorFromUrl(
    applicationContext: Context,
    imageUrl: String?,
    placeholderResourceId: Int,
    targetWidth: Int = 64,
    targetHeight: Int = 56,
    missingPictureResourceId: Int // Resource ID for missing picture
): BitmapDescriptor? {
    try {
        var loadedBitmap: Bitmap? = null
        Glide.with(applicationContext)
            .asBitmap()
            .load(imageUrl)
            .error(
                Glide.with(applicationContext).load(missingPictureResourceId)
            ) // Load missing picture on error
            .into(object : CustomTarget<Bitmap>() {

                override fun onResourceReady(
                    resource: Bitmap,
                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                ) {
                    loadedBitmap =
                        Bitmap.createScaledBitmap(resource, targetWidth, targetHeight, false)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // Handle case when the load is cleared
                }
            })
        return loadedBitmap?.let {
            BitmapDescriptorFactory.fromBitmap(it)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}

suspend fun loadBitmapDescriptorFromUrl(
    context: Context, url: String, missingPictureResourceId: Int // Resource ID for missing picture
): BitmapDescriptor? {
    return withContext(Dispatchers.IO) {
        try {
            val bitmap: Bitmap = Glide.with(context)
                .asBitmap()
                .load(url)
                .error(
                    Glide.with(context).load(missingPictureResourceId)
                )
                .diskCacheStrategy(DiskCacheStrategy.ALL) // Caches the image
                .override(50, 50) // Resize the image to 50.dp x 50.dp
                .submit()
                .get()

            BitmapDescriptorFactory.fromBitmap(bitmap)
        } catch (e: Exception) {
            null
        }
    }
}

fun createBitmapDescriptorFromResource(
    applicationContext: Context,
    drawableResourceId: Int
): BitmapDescriptor? {

    val drawable = ContextCompat.getDrawable(applicationContext, drawableResourceId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    val canvas = android.graphics.Canvas(bitmap)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}
