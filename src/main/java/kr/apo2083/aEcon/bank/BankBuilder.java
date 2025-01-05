package kr.apo2083.aEcon.bank;

import org.bukkit.entity.Player;

public class BankBuilder {
    protected final String name;
    protected final Player owner;
    public BankBuilder(String name, Player owner) {
        this.name=name;
        this.owner=owner;
    }

    public Bank build() {
        return new Bank(this);
    }
}
