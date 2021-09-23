package core.basesyntax.models;

import java.util.Objects;

public class Fruit {
    private String name;
    private int amount;

    public Fruit(String name, int amount) {
        if (name == null || amount < 0) {
            throw new RuntimeException("Invalid data in Fruit constructor");
        } else {
            this.name = name;
            this.amount = amount;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.length() == 0) {
            throw new RuntimeException("Invalid data in "
                    + "Fruit setName(String name)");
        }
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        if (amount < 0) {
            throw new RuntimeException("Invalid data in "
                    + "Fruit setAmount(int amount)");
        }
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Fruit fruit = (Fruit) o;
        return amount == fruit.amount && Objects.equals(name, fruit.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, amount);
    }

    @Override
    public Fruit clone() {
        return new Fruit(name, amount);
    }
}
