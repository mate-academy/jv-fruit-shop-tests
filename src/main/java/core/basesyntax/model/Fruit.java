package core.basesyntax.model;

public enum Fruit {
    APPLE("apple"),
    BANANA("banana");


    private String fruitName;

    Fruit(String fruitName) {
        this.fruitName = fruitName;
    }

    public String getFruitName() {
        return fruitName;
    }
}
