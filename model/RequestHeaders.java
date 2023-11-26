package model;

public enum RequestHeaders {
    CONTENT("Content-Type"),
    KEY("api-key"),
    EXPIRES("api-expires"),
    SIGNATURE("api-signature"),
    //
    JSON("application/json");
    //
    private final String value;

    RequestHeaders(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
