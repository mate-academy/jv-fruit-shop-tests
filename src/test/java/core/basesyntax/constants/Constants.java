package core.basesyntax.constants;

public enum Constants {
    BANANA("banana"),
    APPLE("apple"),
    ORANGE("orange");

    private final String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
