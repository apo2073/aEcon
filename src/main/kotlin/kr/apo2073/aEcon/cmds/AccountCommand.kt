package kr.apo2073.aEcon.cmds

import kr.apo2073.aEcon.utilities.Messages.translate
import kr.apo2073.aEcon.utilities.sendMessage
import net.kyori.adventure.text.Component
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.plugin.java.JavaPlugin

class AccountCommand(plugin: JavaPlugin):TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        cmd: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        sender.sendMessage(Component.text(translate("not.support")), true)
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        cmd: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String> {
        var tab= mutableListOf<String>()
        if (args.size==1) {
            // player account list
        }
        if (args.size==2) {
            tab.apply {
                add("bal")
                add("info")
                add("remittance")
                add("remove")
                add("create")
            }
        }
        if (args.size==3) {
            tab.add("account-num")
        }
        if (args.size==4) {
            for (i in 1..10) tab.add((10*i).toString())
            tab.add("all")
        }
        return tab
    }
}