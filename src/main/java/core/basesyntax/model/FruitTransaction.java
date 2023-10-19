package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private String name;
    private Integer quantity;
    private Operation operation;

    public FruitTransaction(String name, Integer quantity, Operation operation) {
        this.name = name;
        this.quantity = quantity;
        this.operation = operation;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Operation getOperation() {
        return operation;
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
        return Objects.equals(name, that.name)
                && Objects.equals(quantity, that.quantity)
                && operation == that.operation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, quantity, operation);
    }
}
