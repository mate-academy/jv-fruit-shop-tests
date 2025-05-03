package core.basesyntax.model;

public enum FruitsInStore {
    APPLE("apple"),
    BANANA("banana");

    private final String fruit;

    FruitsInStore(String fruit) {
        this.fruit = fruit;
    }

    public String getCode() {
        return fruit;
    }
}
