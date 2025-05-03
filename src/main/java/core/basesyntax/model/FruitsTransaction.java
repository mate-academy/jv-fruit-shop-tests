package core.basesyntax.model;

import java.util.Objects;

public class FruitsTransaction {
    private final Operation operation;
    private final String name;
    private final int quantity;

    public FruitsTransaction(String operationCode, String name, int quantity) {
        this.operation = Operation.getByCode(operationCode);
        this.name = name;
        this.quantity = quantity;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getName() {
        return name;
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
        FruitsTransaction that = (FruitsTransaction) o;
        return quantity == that.quantity && operation == that.operation
                && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, name, quantity);
    }
}
