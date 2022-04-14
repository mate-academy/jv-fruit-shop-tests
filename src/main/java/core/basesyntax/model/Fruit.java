package core.basesyntax.model;

import java.util.Objects;

public class Fruit {
    private String name;

    public Fruit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object fruit) {
        if (this == fruit) {
            return true;
        }
        if (fruit == null || getClass() != fruit.getClass()) {
            return false;
        }
        Fruit fruitCheck = (Fruit) fruit;
        return Objects.equals(name, fruitCheck.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
