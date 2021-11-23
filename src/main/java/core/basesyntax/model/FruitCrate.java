package core.basesyntax.model;

import java.util.Objects;

public class FruitCrate {
    private final String name;
    private int quantity;

    public FruitCrate(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object fruitCrate) {
        if (this == fruitCrate) {
            return true;
        }
        if (fruitCrate == null || getClass() != fruitCrate.getClass()) {
            return false;
        }
        FruitCrate castedFruitCrate = (FruitCrate) fruitCrate;
        return Objects.equals(this.name, castedFruitCrate.getName())
                && this.quantity == castedFruitCrate.quantity;
    }
}
