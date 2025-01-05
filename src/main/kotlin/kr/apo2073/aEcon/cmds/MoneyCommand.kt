package kr.apo2073.aEcon.cmds

import kr.apo2073.aEcon.AEcon
import kr.apo2073.aEcon.utilities.Messages.translate
import kr.apo2073.aEcon.utilities.sendMessage
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class MoneyCommand(plugin: JavaPlugin):TabExecutor {
    init {
        plugin.getCommand("money")?.apply {
            setExecutor(this@MoneyCommand)
            tabCompleter= this@MoneyCommand
        }
    }
    private val econ= AEcon.econ
    override fun onCommand(
        sender: CommandSender,
        cmd: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (sender !is Player) {return true}
        if (args.isEmpty()) return false
        if (args[0]=="bal") {
            performBal(sender)
            return true
        }
        if (args.size<3) {sendUsage(sender);return true}
        val player=Bukkit.getPlayer(args[1]) ?: run {
            sender.sendMessage(Component.text(translate("not.found.player")), true)
            return true
        }
        val amount=args[2].toDoubleOrNull() ?: run {
            sender.sendMessage(Component.text(translate("enter.correct.value")), true)
            return true
        }
        if (args[0]=="send") {
            performSend(sender, player, amount)
        }
        return true
    }

    private fun performSend(sender: Player, player: Player, amount : Double) {
        if (!econ.has(sender, amount)) {sender.sendMessage(Component.text(translate("no.enough.money")));return}
        econ.withdrawPlayer(sender, amount)
        econ.depositPlayer(player, amount)
        sender.sendMessage(Component.text(translate("command.money.send.sender")
            .replace("{player}", sender.name).replace("{amount}", amount.toString())), true)
        player.sendMessage(Component.text(translate("command.money.send.recipient")
            .replace("{player}", player.name).replace("{amount}", amount.toString())), true)
    }

    private fun performBal(sender: Player) {
        sender.sendMessage(
            Component.text(translate("command.money.bal").replace("{amount}", econ.getBalance(sender).toString())),
            true
        )
    }

    private fun sendUsage(sender: CommandSender) {
        val message= translate("command.money.usage").split("|")
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
                add("send")
                add("bal")
            }
        } else if (args.size==2) {
            Bukkit.getOnlinePlayers().forEach { tab.add(it.name) }
        } else {
            for (i in 1..10) tab.add((10*i).toString())
        }
        return tab
    }
}