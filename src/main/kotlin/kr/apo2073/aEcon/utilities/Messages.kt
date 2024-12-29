package kr.apo2073.aEcon.utilities

import com.google.gson.Gson
import com.google.gson.JsonObject
import kr.apo2073.aEcon.AEcon
import java.io.File

object Messages {
    private val plugin=AEcon.instance
    private var language= plugin.config.getString("lang", "ko")
    private var langFile=File("${plugin.dataFolder}/languages", "${language}.json")
    init {
        plugin.reloadConfig()
        language= plugin.config.getString("lang", "ko")
        langFile=File("${plugin.dataFolder}/languages", "${language}.json")
    }
    fun translate(text:String):String {
        try {
            val lang= langFile.readText()
            val langJson=Gson().fromJson(lang, JsonObject::class.java)
            return langJson.get(text).asString
                .replace("{lang}", language.toString())
                .replace("{unit}", Manager().getUnit())
                .replace("&", "§")
        } catch (e: Exception) { return text }
    }
}