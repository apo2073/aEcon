package kr.apo2083.aEcon.bank;

import kr.apo2073.aEcon.AEcon;
import kr.apo2073.aEcon.utilities.Messages;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;

import java.util.List;

public class Bank {
    private final String name;
    private final Player owner;
    private List<Player> member;

    private AEcon aEcon=AEcon.Companion.getInstance();
    private Economy econ=AEcon.Companion.getEcon();

    public Bank(BankBuilder builder) {
        this.name= builder.name;
        this.owner= builder.owner;
        econ.createBank(name, owner);
    }

    public void joinMember(Player member) {

    }

    public String getName() {
        return name;
    }

    public Player getOwner() {
        return owner;
    }
}
