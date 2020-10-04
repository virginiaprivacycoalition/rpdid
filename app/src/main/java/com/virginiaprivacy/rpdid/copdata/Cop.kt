package com.virginiaprivacy.rpdid.copdata

import kotlinx.serialization.Required
import kotlinx.serialization.Serializable
import java.util.*
import kotlin.properties.Delegates
import kotlin.system.measureTimeMillis

@Serializable
data class Cop(
    val title: String,
    val firstName: String,
    val lastName: String,
    val nickName: String = "",
    private val copPhotos: MutableList<CopPhoto> = mutableListOf()
) {
    val displayPhoto get() = photos.random()
    val formattedName: String
        get() = getFormattedCopName(this)
    @Required
    var id by Delegates.notNull<Int>()
    val photos = mutableListOf<CopPhoto>()

    init {
        id = AUTO_ID++
        if (copPhotos.isNotEmpty()) {
            photos.addAll(copPhotos)
        }
//        copsDir.list()?.let {
//            if (!it.contains(id.toString())) {
//                // This means the cop hasn't been saved before
//                save()
//            }
//        }
    }

    fun addPhoto(vararg photo: CopPhoto) {
        val time = measureTimeMillis {
            Collections.synchronizedList(copPhotos).addAll(photo)
        }
    }

    fun url() = "/cops/${id}"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cop

        if (id != other.id) return false
        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (nickName != other.nickName) return false
        if (photos != other.photos) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + nickName.hashCode()
        result = 31 * result + photos.hashCode()
        return result
    }


    companion object {

        var AUTO_ID = 0

        @Deprecated("Old method of loading cops. Use com.virginiaprivacy.rpdid.copdata.Cop.loadAll()")
        private fun getNameFromFolder(name: String): List<String> {
            return when (name.count { it == '_' }) {
                3 -> {
                    name.split("_", limit = 4).run {
                        this
                    }
                }
                2 -> name.split("_", limit = 3).toMutableList().apply { add(0, "") }
                else -> name.split("_", limit = 2).toMutableList().apply {
                    add(0, "")
                    add(2, "")
                }

            }
        }

        fun getFormattedCopName(cop: Cop): String {
            var output = ""
            cop.title.capitalize(Locale.ROOT).run {
                if (this.isNotEmpty()) {
                    output += "${this}. "
                }
            }
            cop.firstName.capitalize(Locale.ROOT).run {
                if (this.isNotEmpty()) {
                    output += if (output.isNotEmpty()) {
                        this
                    } else {
                        " $this"
                    }
                }
            }
            cop.nickName.run {
                if (this.isNotEmpty() && cop.nickName.replace("\"", "").isNotEmpty()) {
                    output += " (${this.replace("\"", "").capitalize(Locale.getDefault())}) "
                }
            }
            cop.lastName.capitalize(Locale.getDefault()).run {
                if (this.isNotEmpty()) {
                    output += " $this"
                }
            }
            return output
        }
    }
}