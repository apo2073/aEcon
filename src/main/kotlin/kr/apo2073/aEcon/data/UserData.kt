package kr.apo2073.aEcon.data

import kr.apo2073.aEcon.AEcon
import org.bukkit.OfflinePlayer
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File

class UserData(private val player: OfflinePlayer) {
    private val plugin = AEcon.instance
    private var file = File("${plugin.dataFolder}/userdata", "${player.uniqueId}.yml")
    private var config = YamlConfiguration.loadConfiguration(file)
    private var money = 0.0

    init {
        file = File("${plugin.dataFolder}/userdata", "${player.uniqueId}.yml")
        config = YamlConfiguration.loadConfiguration(file)
        money = config.getDouble("money")
        plugin.reloadConfig()
    }

    fun get(): YamlConfiguration = config
    fun set() {
        try {
            config.set("money", money)
            config.save(file)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getBal(): Double = money

    fun withdraw(amount: Double) {
        money-=amount;set()
    }
    fun deposit(amount: Double) {
        money+=amount;set()
    }

    fun isExist(): Boolean = file.exists()

    fun getPlayer(): Player? = player.player

    fun remove() = file.delete()
}