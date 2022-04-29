package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private final String operation;
    private final String fruit;
    private final Integer quantity;

    public FruitTransaction(String operation, String fruit, Integer quantity) {
        this.operation = operation;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public String getOperation() {
        return operation;
    }

    public String getFruit() {
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
        FruitTransaction that = (FruitTransaction) o;
        return Objects.equals(operation, that.operation)
                && Objects.equals(fruit, that.fruit)
                && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, quantity);
    }
}
