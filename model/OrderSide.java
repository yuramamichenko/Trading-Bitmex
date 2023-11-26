package model;

public enum OrderSide {
    BUY("Buy"),
    SELL("Sell");

    public final String value;

    OrderSide(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
    }
