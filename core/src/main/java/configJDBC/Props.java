package configJDBC;

public enum Props {
    DRIVER("db.driver"),
    URL("db.url"),
    USER("db.user"),
    PASSWORD("db.password");

    private String key;

    Props(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
