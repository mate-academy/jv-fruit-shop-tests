package core.basesyntax.models;

import java.util.Objects;

public final class Transaction {
    private final String type;
    private final Fruit fruit;
    private final Integer quantity;

    public Transaction(String type, Fruit fruit, Integer quantity) {
        this.type = type;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transaction that = (Transaction) o;
        return Objects.equals(type, that.type) && Objects.equals(fruit, that.fruit)
                       && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, fruit, quantity);
    }
}
