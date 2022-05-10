package core.basesyntax.model;

import java.util.Objects;

public class Fruit {
    private String name;
    private int amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Fruit{"
                + "name='" + name + '\''
                + ", amount=" + amount
                + '}';
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
        return amount == fruit.amount && name.equals(fruit.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, amount);
    }
}
