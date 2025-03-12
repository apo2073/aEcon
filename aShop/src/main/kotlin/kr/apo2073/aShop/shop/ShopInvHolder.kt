package kr.apo2073.aShop.shop

import kr.apo2073.aShop.AShop
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.persistence.PersistentDataType

class ShopInvHolder:InventoryHolder, Listener {
    internal var inventory=Bukkit.createInventory(this, 9*6,Component.text(""))
    override fun getInventory(): Inventory {
        return inventory
    }

    private val perKey=NamespacedKey(AShop.plugin, "perPage")
    private val nexKey=NamespacedKey(AShop.plugin, "nexPage")
    private val shopKey=NamespacedKey(AShop.plugin, "shop")
    @EventHandler
    fun InventoryClickEvent.changePage() {
        if (inventory.getHolder(false) !is ShopInvHolder) return
        isCancelled=true
        val item=currentItem ?: return

        val name=(inventory.getItem(inventory.size-1) ?: return)
            .persistentDataContainer
            .get(shopKey, PersistentDataType.STRING) ?: return
        val shopInvHolder=Shop(name)

        if (item.itemMeta.persistentDataContainer.has(perKey)) {
            (whoClicked as Player).openInventory(shopInvHolder.getPage(
                item.persistentDataContainer.get(perKey, PersistentDataType.INTEGER)  ?: return
            ) ?: return)
        }
        if (item.itemMeta.persistentDataContainer.has(nexKey)) {
            (whoClicked as Player).openInventory(shopInvHolder.getPage(
                item.persistentDataContainer.get(nexKey, PersistentDataType.INTEGER)  ?: return
            ) ?: return)
        }
    }

    @EventHandler
    fun InventoryClickEvent.onBuyOrSell() {
        if (inventory.getHolder(false) !is ShopInvHolder) return
        isCancelled=true
        if (click.isLeftClick) { // 구매
            if (click.isShiftClick) {

            } else if (click.isKeyboardClick) {

            } else {

            }
        }
        if (click.isRightClick) { // 판매
            if (click.isShiftClick) {

            } else if (click.isKeyboardClick) {

            } else {

            }
        }
    }
}