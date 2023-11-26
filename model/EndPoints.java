package model;

public enum EndPoints {
    ORDER("/api/v1/order"),
    POSITION("/api/v1/position"),
    CANSEL_ALL_ORDERS("/api/v1/order/all");

    public final String path;

    EndPoints(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }
}