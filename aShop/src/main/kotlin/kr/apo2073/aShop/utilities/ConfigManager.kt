package kr.apo2073.aShop.utilities

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class ConfigManager {
    companion object {
        fun getFile(plugin:JavaPlugin, file:String):File {
            return File("${plugin.dataFolder}", file)
        }

        fun getConfig(file: File):YamlConfiguration {
            return YamlConfiguration.loadConfiguration(file)
        }
    }
}