package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private Fruit fruit;
    private String operation;
    private Integer quantity;

    public FruitTransaction(String operation, Fruit fruit, int quantity) {
        this.operation = operation;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public String getOperation() {
        return operation;
    }

    public int getQuantity() {
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
        FruitTransaction that = (FruitTransaction) o;
        return Objects.equals(fruit, that.fruit) && Objects.equals(operation, that.operation)
                && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruit, operation, quantity);
    }
}

