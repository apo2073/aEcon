package kr.apo2073.aEcon.economy

import kr.apo2073.aEcon.utilities.Manager
import kr.apo2073.aEcon.utilities.Messages.translate
import kr.apo2073.aEcon.data.UserData
import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.economy.EconomyResponse
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.plugin.java.JavaPlugin

class Econs(private val plugin: JavaPlugin):Economy {
    private val manager=Manager()

    override fun isEnabled(): Boolean = plugin.isEnabled
    override fun getName(): String = "aEcon"

    override fun hasBankSupport(): Boolean = false

    override fun fractionalDigits(): Int = 0

    override fun format(amount: Double): String = amount.toString()+manager.getUnit()

    override fun currencyNamePlural(): String = currencyNameSingular()

    override fun currencyNameSingular(): String = manager.getUnit()

    @Deprecated("Deprecated in Java", ReplaceWith("hasAccount(Bukkit.getPlayer(playerName))", "org.bukkit.Bukkit"))
    override fun hasAccount(playerName: String): Boolean {
        return hasAccount(Bukkit.getPlayer(playerName))
    }

    override fun hasAccount(player: OfflinePlayer?): Boolean {
        return UserData(player ?: return false).isExist()
    }

    @Deprecated("Deprecated in Java",
        ReplaceWith("hasAccount(Bukkit.getPlayer(playerName), world)", "org.bukkit.Bukkit")
    )
    override fun hasAccount(playerName: String, world: String): Boolean {
        return hasAccount(Bukkit.getPlayer(playerName), world)
    }

    override fun hasAccount(player: OfflinePlayer?, world: String): Boolean {
        return hasAccount(player)
    }

    @Deprecated("Deprecated in Java", ReplaceWith("getBalance(Bukkit.getPlayer(playerName))", "org.bukkit.Bukkit"))
    override fun getBalance(playerName: String): Double {
        return getBalance(Bukkit.getPlayer(playerName))
    }

    override fun getBalance(player: OfflinePlayer?): Double {
        return UserData(player ?: return 0.0).getBal()
    }

    @Deprecated("Deprecated in Java",
        ReplaceWith("getBalance(Bukkit.getPlayer(playerName), world)", "org.bukkit.Bukkit")
    )
    override fun getBalance(playerName: String, world: String): Double {
        return getBalance(Bukkit.getPlayer(playerName), world)
    }

    override fun getBalance(player: OfflinePlayer?, world: String): Double {
        return getBalance(player)
    }

    @Deprecated("Deprecated in Java", ReplaceWith("has(Bukkit.getPlayer(playerName), amount)", "org.bukkit.Bukkit"))
    override fun has(playerName: String, amount: Double): Boolean {
        return has(Bukkit.getPlayer(playerName), amount)
    }

    override fun has(player: OfflinePlayer?, amount: Double): Boolean {
        val bal = getBalance(player)
        return bal>=amount
    }

    @Deprecated("Deprecated in Java",
        ReplaceWith("has(Bukkit.getPlayer(playerName), world, amount)", "org.bukkit.Bukkit")
    )
    override fun has(playerName: String, world: String, amount: Double): Boolean {
        return has(Bukkit.getPlayer(playerName), world, amount)
    }

    override fun has(player: OfflinePlayer?, world: String, amount: Double): Boolean {
        return has(player, amount)
    }

    @Deprecated("Deprecated in Java",
        ReplaceWith("withdrawPlayer(Bukkit.getPlayer(playerName), amount)", "org.bukkit.Bukkit")
    )
    override fun withdrawPlayer(playerName: String, amount: Double): EconomyResponse {
        return withdrawPlayer(Bukkit.getPlayer(playerName), amount)
    }

    override fun withdrawPlayer(player: OfflinePlayer?, amount: Double): EconomyResponse {
        val userData=
            UserData(player ?: return EconomyResponse(amount, amount, EconomyResponse.ResponseType.FAILURE, translate("not.found.player")))
        userData.withdraw(amount)
        return EconomyResponse(amount, userData.getBal(), EconomyResponse.ResponseType.SUCCESS, null)
    }

    @Deprecated("Deprecated in Java",
        ReplaceWith("withdrawPlayer(Bukkit.getPlayer(playerName), world, amount)", "org.bukkit.Bukkit")
    )
    override fun withdrawPlayer(playerName: String, world: String, amount: Double): EconomyResponse {
        return withdrawPlayer(Bukkit.getPlayer(playerName), world, amount)
    }

    override fun withdrawPlayer(player: OfflinePlayer?, world: String, amount: Double): EconomyResponse {
        return withdrawPlayer(player, amount)
    }

    @Deprecated("Deprecated in Java",
        ReplaceWith("depositPlayer(Bukkit.getPlayer(playerName), amount)", "org.bukkit.Bukkit")
    )
    override fun depositPlayer(playerName: String, amount: Double): EconomyResponse {
        return depositPlayer(Bukkit.getPlayer(playerName), amount)
    }

    override fun depositPlayer(player: OfflinePlayer?, amount: Double): EconomyResponse {
        val userData=
            UserData(player ?: return EconomyResponse(amount, amount, EconomyResponse.ResponseType.FAILURE, translate("not.found.player")))
        userData.deposit(amount)
        return EconomyResponse(amount, userData.getBal(), EconomyResponse.ResponseType.SUCCESS, null)
    }

    @Deprecated("Deprecated in Java",
        ReplaceWith("depositPlayer(Bukkit.getPlayer(playerName), world, amount)", "org.bukkit.Bukkit")
    )
    override fun depositPlayer(playerName: String, world: String, amount: Double): EconomyResponse {
        return depositPlayer(Bukkit.getPlayer(playerName), world, amount)
    }

    override fun depositPlayer(player: OfflinePlayer?, world: String, amount: Double): EconomyResponse {
        return depositPlayer(player, amount)
    }

    @Deprecated("Deprecated in Java",
        ReplaceWith("createBank(bank, Bukkit.getPlayer(playerName))", "org.bukkit.Bukkit")
    )
    override fun createBank(bank: String, playerName: String): EconomyResponse {
        return createBank(bank, Bukkit.getPlayer(playerName))
    }

    override fun createBank(bank: String, player: OfflinePlayer?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, translate("not.support"))
    }

    override fun deleteBank(bank: String): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, translate("not.support"))
    }

    override fun bankBalance(bank: String): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, translate("not.support"))
    }

    override fun bankHas(bank: String, amount: Double): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, translate("not.support"))
    }

    override fun bankWithdraw(bank: String, amount: Double): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, translate("not.support"))
    }

    override fun bankDeposit(bank: String, amount: Double): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, translate("not.support"))
    }

    @Deprecated("Deprecated in Java",
        ReplaceWith("isBankMember(bank, Bukkit.getPlayer(playerName))", "org.bukkit.Bukkit")
    )
    override fun isBankOwner(bank: String, playerName: String): EconomyResponse {
        return isBankMember(bank, Bukkit.getPlayer(playerName))
    }

    override fun isBankOwner(bank: String, player: OfflinePlayer?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, translate("not.support"))
    }

    @Deprecated("Deprecated in Java",
        ReplaceWith("isBankMember(bank, Bukkit.getPlayer(playerName))", "org.bukkit.Bukkit")
    )
    override fun isBankMember(bank: String, playerName: String): EconomyResponse {
        return isBankMember(bank, Bukkit.getPlayer(playerName))
    }

    override fun isBankMember(bank: String, player: OfflinePlayer?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, translate("not.support"))
    }

    override fun getBanks(): MutableList<String> {
        return mutableListOf()
    }

    @Deprecated("Deprecated in Java",
        ReplaceWith("createPlayerAccount(Bukkit.getPlayer(playerName))", "org.bukkit.Bukkit")
    )
    override fun createPlayerAccount(playerName: String): Boolean {
        return createPlayerAccount(Bukkit.getPlayer(playerName))
    }

    override fun createPlayerAccount(player: OfflinePlayer?): Boolean {
        return false
    }

    @Deprecated("Deprecated in Java",
        ReplaceWith("createPlayerAccount(Bukkit.getPlayer(playerName), world)", "org.bukkit.Bukkit")
    )
    override fun createPlayerAccount(playerName: String, world: String): Boolean {
        return createPlayerAccount(Bukkit.getPlayer(playerName), world)
    }

    override fun createPlayerAccount(player: OfflinePlayer?, world: String): Boolean {
        return createPlayerAccount(player)
    }
}