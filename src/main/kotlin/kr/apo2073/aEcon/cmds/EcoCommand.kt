package kr.apo2073.aEcon.cmds

import kr.apo2073.aEcon.AEcon
import kr.apo2073.aEcon.utilities.Messages.translate
import kr.apo2073.aEcon.utilities.sendMessage
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class EcoCommand(private val plugin: JavaPlugin) : TabExecutor {
    private val econ=AEcon.econ
    init {
        plugin.getCommand("eco")?.apply {
            setExecutor(this@EcoCommand)
            tabCompleter= this@EcoCommand
        }
    }

    override fun onCommand(
        sender: CommandSender,
        cmd: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (args.isEmpty()) {
            sendUsage(sender)
        }
        if (args.size==1) {
            if (args[0]=="reload") performReload(sender)
            else sendUsage(sender)
        } else if(args.size==2) {
            if (args[0]=="reload") performReload(sender)
            else sendUsage(sender)
        } else if (args.size==3) {
            if (args[0]=="reload") {
                performReload(sender)
                return true
            }
            val player=Bukkit.getPlayer(args[1]) ?: run {
                sender.sendMessage(Component.text(translate("not.found.player")))
                return true
            }
            val amount=args[2].toDoubleOrNull() ?: run {
                sender.sendMessage(Component.text(translate("enter.correct.value")))
                return true
            }
            when(args[0]) {
                "give"-> {performGive(sender, player, amount)}
                "take"-> {}
                "reset"-> {}
                "set"-> {}
                else-> sendUsage(sender)
            }
        }
        return true
    }

    private fun performReload(sender: CommandSender) {
        plugin.reloadConfig()
        plugin.logger.info(translate("load.file.lang"))
        plugin.logger.info(translate("load.file.config"))
        plugin.logger.info(translate("set.money.unit"))
        sender.sendMessage(Component.text(translate("complete.reload")).decorate(TextDecoration.BOLD), true)
    }

    private fun performGive(sender: CommandSender, player: Player, amount : Double) {
        econ.depositPlayer(player, amount)
        sender.sendMessage(Component.text(translate("command.eco.give")
            .replace("{player}", player.name)
            .replace("{amount}", amount.toString())))
    }

    private fun sendUsage(sender: CommandSender) {
        val message= translate("command.eco.usage").split("|")
        message.forEach {
            sender.sendMessage(Component.text(it), true)
        }
    }

    override fun onTabComplete(
        sender: CommandSender,
        cmd: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String> {
        val tab= mutableListOf<String>()
        if (args.size==1) {
            tab.apply {
                add("reload")
                add("give")
                add("take")
                add("reset")
                add("set")
            }
        } else if (args.size==2) {
            if (args[0]=="reload") return tab
            Bukkit.getOnlinePlayers().forEach { tab.add(it.name) }
        } else {
            if (args[0]=="reload") return tab
            for (i in 1..10) tab.add((10*i).toString())
        }
        return tab
    }
}