package kr.apo2073.aEcon.data

import kr.apo2073.aEcon.AEcon
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class BankData(private val name:String) {
    private val plugin = AEcon.instance
    private var file = File("${plugin.dataFolder}/userdata", "$name.yml")
    private var config = YamlConfiguration.loadConfiguration(file)

    init {
        file = File("${plugin.dataFolder}/userdata", "$name.yml")
        config = YamlConfiguration.loadConfiguration(file)
        plugin.reloadConfig()
    }

    fun get(): YamlConfiguration = config
    fun set() {
        try {
            config.save(file)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setValue(path:String, value:Any) {
        config.set(path, value)
        set()
    }

    fun owner(): OfflinePlayer? {
        return Bukkit.getOfflinePlayer(config.getString("bank.owner") ?: return null)
    }

    fun getMoney():Int {
        return config.getInt("bank.balance")
    }

    fun getMembers():MutableList<String> {
        return config.getStringList("bank.members")
    }
    fun addMembers(name: String) {
        val m=getMembers()
        m.add(name)
        setValue("bank.members", m)
    }
}