package com.virginiaprivacy.rpdid.copdata

import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.core.graphics.drawable.toDrawable
import com.virginiaprivacy.rpdid.context
import kotlinx.serialization.Serializable
import java.io.File
import kotlin.properties.Delegates

@Serializable
data class CopPhoto(private val path: String = "") {
    var id by Delegates.notNull<Int>()

    var apiPath: String

    init {
        id = ++AUTO_ID
        apiPath = "api/photo/$id"
    }

    fun getPhoto(): File = File(path)


    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return 31 * id.hashCode() * path.hashCode()
    }

    override fun toString(): String {
        return super.toString()
    }

    fun getDrawable(): Drawable {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeDrawable(ImageDecoder.createSource(context.assets, path))
            } else {
                BitmapFactory.decodeStream(context.assets.open(path)).toDrawable(context.resources)
            }
    }

    companion object {
        var AUTO_ID = 0
    }
}