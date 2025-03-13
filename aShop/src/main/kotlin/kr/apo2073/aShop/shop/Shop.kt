package kr.apo2073.aShop.shop

import kr.apo2073.aShop.AShop
import kr.apo2073.aShop.utilities.ConfigManager
import kr.apo2073.aShop.utilities.str2Component
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import java.io.File

class Shop(shop: String, line:Int?=null) {
    private var name=PlainTextComponentSerializer.plainText().serialize(shop.str2Component())
    private var file:File = ConfigManager.getFile(AShop.plugin, "shop/${name}.yml")
    private var config:YamlConfiguration = YamlConfiguration.loadConfiguration(file)
    private val shopInvHolder=ShopInvHolder()

    init {
        if (line != null) config.set("shop.line", line)
        if (config.getString("shop.name")==null) config.set("shop.name", shop)
        config.save(file)
        shopInvHolder.inventory = Bukkit.createInventory(
            shopInvHolder, 9 * config.getInt("shop.line", 6),
            shop.str2Component()
        ).apply {
            this.setItem(
                shopInvHolder.getInventory().size-1,
                ItemStack(Material.LIME_STAINED_GLASS_PANE).apply {
                    val meta=this.itemMeta
                    meta.displayName("&a다음 페이지".str2Component())
                    meta.persistentDataContainer.set(
                        NamespacedKey(AShop.plugin, "shop"),
                        PersistentDataType.STRING, shop
                    )
                    this.itemMeta=meta
                }
            )
            this.setItem(
                shopInvHolder.getInventory().size-8,
                ItemStack(Material.RED_STAINED_GLASS_PANE).apply {
                    val meta=this.itemMeta
                    meta.displayName("&c이전 페이지".str2Component())
                    this.itemMeta=meta
                }
            )
        }
    }

    private fun reload() {
        file = ConfigManager.getFile(AShop.plugin, "shop/${name}.yml")
        config = YamlConfiguration.loadConfiguration(file)
    }

    fun setItem(item:ItemStack, slot:Int, page:Int=1) {
        val inv=getPage(page) ?: shopInvHolder.inventory
        inv.setItem(slot, item)
        for (i in 0..inv.contents.size) {
            config.set("shop.page.$page.$i", inv.contents[i])
        }
        config.save(file) // ▼▲
    }

    fun getPage(page: Int):Inventory? {
        reload()
        val inv=shopInvHolder.getInventory()
        val pages=config.getConfigurationSection("shop.page.${page}") ?: return null
        for (slot in pages.getKeys(false)) {
            val item=pages.getItemStack(slot) ?: continue
            if (item.itemMeta.displayName()=="&c이전 페이지".str2Component()) {
                item.apply {
                    val meta=this.itemMeta
                    meta.persistentDataContainer.set(
                        NamespacedKey(AShop.plugin, "perPage"),
                        PersistentDataType.INTEGER, page-1
                    )
                    this.itemMeta=meta
                }
            }
            if (item.itemMeta.displayName()=="&a다음 페이지".str2Component()) {
                item.apply {
                    val meta=this.itemMeta
                    meta.persistentDataContainer.set(
                        NamespacedKey(AShop.plugin, "nexPage"),
                        PersistentDataType.INTEGER, page+1
                    )
                    this.itemMeta=meta
                }
            }
            inv.setItem(slot.toInt(), item)
        }
        return inv
    }

    fun getName():String = name

    fun open(player:Player) {
        player.openInventory(shopInvHolder.getInventory())
    }
}