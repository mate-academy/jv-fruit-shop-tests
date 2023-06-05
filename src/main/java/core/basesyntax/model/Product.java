package core.basesyntax.model;

public enum Product {
    BANANA("banana"),
    APPLE("apple");

    private String name;

    Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
