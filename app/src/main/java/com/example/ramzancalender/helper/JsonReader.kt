package com.example.ramzancalender.helper
import android.content.Context
import org.json.JSONArray

class JsonReader(private val context: Context) {
    fun readJsonFile(fileName: String): JSONArray? {
        val jsonString: String
        try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            jsonString = String(buffer)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return JSONArray(jsonString)
    }
}


