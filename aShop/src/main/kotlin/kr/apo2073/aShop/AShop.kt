package kr.apo2073.aShop

import org.bukkit.plugin.java.JavaPlugin

class AShop : JavaPlugin() {
    companion object {
        lateinit var plugin: JavaPlugin
    }
    override fun onEnable() {
        plugin=this
    }
}
