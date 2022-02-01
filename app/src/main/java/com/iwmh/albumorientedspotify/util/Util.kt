package com.iwmh.albumorientedspotify.util

import android.content.Context
import java.io.IOException
import java.lang.Exception

class Util {
    companion object {
        fun loadJSONFromAsset(context: Context, fileName: String): String? {
            val json: String? = try {
                val inputStream = context.assets.open(fileName)
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                String(buffer, Charsets.UTF_8)
            } catch (ex: IOException) {
                throw ex
            } catch (ex: Exception) {
                throw ex
            }
            return json
        }
    }
}