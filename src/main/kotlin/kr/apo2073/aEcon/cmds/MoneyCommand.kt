package kr.apo2073.aEcon.cmds

import kr.apo2073.aEcon.AEcon
import kr.apo2073.aEcon.utilities.Messages.translate
import kr.apo2073.aEcon.utilities.sendMessage
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class MoneyCommand:TabExecutor {
    private val econ= AEcon.econ
    override fun onCommand(
        sender: CommandSender,
        cmd: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (args.isEmpty()) return false
        sender.sendMessage(Component.text(translate("not.support"), NamedTextColor.WHITE, TextDecoration.BOLD), true)
        return true
    }

    private fun performSend(sender: Player, player: Player, amount : Double) {
        if (!econ.has(sender, amount)) {sender.sendMessage(Component.text(translate("no.enough.money")));return}
        econ.withdrawPlayer(sender, amount)
        econ.depositPlayer(player, amount)
        sender.sendMessage(Component.text(translate("command.money.send.sender").replace("{player}", sender.name)))
        player.sendMessage(Component.text(translate("command.money.send.recipient").replace("{player}", player.name)))
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
            }
        } else if (args.size==2) {
            Bukkit.getOnlinePlayers().forEach { tab.add(it.name) }
        } else {
            for (i in 1..10) tab.add((10*i).toString())
        }
        return tab
    }
}