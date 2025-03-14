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

class ShopItemManager(internal val shop: Shop, private val page:Int):InventoryHolder, Listener {
    internal var inv=Bukkit.createInventory(this, 9*6,
        "${shop.getName()} - 관리".str2Component()
    )

    @EventHandler
    fun InventoryClickEvent.onClick() {
        if (inventory.holder !is ShopItemManager) return
        isCancelled=true
        val item=currentItem ?: return
        if (item.type == Material.RED_STAINED_GLASS_PANE) return
        whoClicked.closeInventory()
        if (item.type == Material.LIME_STAINED_GLASS_PANE) {
            for (i in 0..<inventory.size) {

            }
            return
        }
        whoClicked.openInventory(ShopAddItemHolder(this@ShopItemManager, item).inventory)
    }

    override fun getInventory(): Inventory {
        try {
            inv.contents=shop.getPage(page)?.contents ?: return inv
            (shop.getPage(page)?.size?.minus(1))?.rangeUntil(inv.size)?.forEach { i ->
                inv.setItem(i, ItemStack(Material.RED_STAINED_GLASS_PANE).apply {
                    val meta=this.itemMeta
                    meta.isHideTooltip=true
                    this.itemMeta=meta
                })
            }
            shop.getPage(page)?.setItem(
                shop.getPage(page)?.size?.minus(1)!!,
                shop.getPage(page)?.getItem(shop.getPage(page)?.size?.minus(1)!!)
                    .apply { this?.type=Material.RED_STAINED_GLASS_PANE }
            )
            shop.getPage(page)?.setItem(
                shop.getPage(page)?.size?.minus(9)!!,
                shop.getPage(page)?.getItem(shop.getPage(page)?.size?.minus(8)!!)
                    .apply { this?.type=Material.RED_STAINED_GLASS_PANE }
            )
            shop.getPage(page)?.setItem(
                shop.getPage(page)?.size?.minus(5)!!,
                ItemStack(Material.LIME_STAINED_GLASS_PANE).apply {
                    val meta=this.itemMeta
                    meta.displayName("&a&l완료".str2Component())
                    this.itemMeta=meta
                }
            )
        } catch (e:NullPointerException) {
            return inv
        }
        return inv
    }
}