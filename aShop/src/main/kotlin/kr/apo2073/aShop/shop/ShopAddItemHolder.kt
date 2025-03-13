package kr.apo2073.aShop.shop

import kr.apo2073.aShop.utilities.str2Component
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack

class ShopAddItemHolder(private val shopItem: ShopItemManager, private val itemStack: ItemStack):InventoryHolder, Listener {
    private val inv=Bukkit.createInventory(this, 9*3,
        "${shopItem.shop.getName()} - 아이템 추가".str2Component()
    )

    @EventHandler
    fun InventoryClickEvent.onClick() {

    }

    override fun getInventory(): Inventory {

        return inv
    }
}