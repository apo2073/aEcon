package kr.apo2073.aEcon.utilities

import kr.apo2073.aEcon.cmds.EcoCommand
import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

fun registryCommands(plugin: JavaPlugin) {
    EcoCommand(plugin)
}

fun CommandSender.sendMessage(component: Component, prefix:Boolean) {
    this.sendMessage(if (prefix) {
        Manager().getPrefix().append(component)
    } else {component})
}