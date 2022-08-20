package core.basesyntax.model;

import java.util.Objects;

public class Fruit {
    private final String fruitType;
    private int fruitQuantity;

    public Fruit(String fruitType, int fruitQuantity) {
        this.fruitType = fruitType;
        this.fruitQuantity = fruitQuantity;
    }

    public String getFruitType() {
        return fruitType;
    }

    public int getFruitQuantity() {
        return fruitQuantity;
    }

    public void setFruitQuantity(int fruitQuantity) {
        this.fruitQuantity = fruitQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fruit)) {
            return false;
        }
        Fruit fruit = (Fruit) o;
        return fruitQuantity == fruit.fruitQuantity
                && Objects.equals(fruitType, fruit.fruitType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruitType, fruitQuantity);
    }
}
