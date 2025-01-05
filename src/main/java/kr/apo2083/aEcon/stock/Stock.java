package kr.apo2083.aEcon.stock;

import org.bukkit.entity.Player;

public class Stock {
    private final String name;
    private final Player owner;
    public Stock(StockBuilder builder) {
        this.name=builder.name;
        this.owner= builder.owner;
    }
}
