package kr.apo2083.aEcon.stock;

import org.bukkit.entity.Player;

public class StockBuilder {
    protected final String name;
    protected final Player owner;
    public StockBuilder(String name, Player owner) {
        this.name=name;
        this.owner=owner;
    }

    public Stock build() {
        return new Stock(this);
    }
}
