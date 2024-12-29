package kr.apo2073.aEcon

import kr.apo2073.aEcon.economy.Econs
import kr.apo2073.aEcon.utilities.Messages.translate
import kr.apo2073.aEcon.utilities.registryCommands
import net.milkbowl.vault.economy.Economy
import org.bstats.bukkit.Metrics
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.java.JavaPlugin

class AEcon : JavaPlugin() {
    companion object {
        lateinit var instance: AEcon
        lateinit var econ: Economy
    }
    private val pluginId=24260
    override fun onEnable() {
        instance = this
        saveResource("languages/ko.json", false)
//        saveResource("languages/en.json", false)
        saveDefaultConfig()

        server.servicesManager.register(
            Economy::class.java,
            Econs(this),
            this,
            ServicePriority.Normal
        )

        if (!setupEconomy()) {
            logger.warning(translate("not.found.plugin.vault"))
            this.server.pluginManager.disablePlugin(this)
            return
        }

        registryCommands(this)
        Metrics(this, pluginId)
    }

    private fun setupEconomy(): Boolean {
        val vaultPlugin = server.pluginManager.getPlugin("Vault")
        if (vaultPlugin == null || !vaultPlugin.isEnabled) {
            return false
        }
        val rsp = server.servicesManager.getRegistration(Economy::class.java) ?: return false
        econ = rsp.provider
        return true
    }
}
