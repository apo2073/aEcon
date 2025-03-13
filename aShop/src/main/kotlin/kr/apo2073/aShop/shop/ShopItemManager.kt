package kr.apo2073.aShop.shop

import kr.apo2073.aShop.utilities.str2Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack

class ShopItemManager(internal val shop: Shop):InventoryHolder, Listener {
    private val inv=Bukkit.createInventory(this, 9*6,
        "${shop.getName()} - 관리".str2Component()
    )

    @EventHandler
    fun InventoryClickEvent.onClick() {

    }

    override fun getInventory(): Inventory {
        val non=ItemStack(Material.RED_STAINED_GLASS_PANE).apply {
            val meta=this.itemMeta
            meta.isHideTooltip=true
            this.itemMeta=meta
        }

        return inv
    }
}