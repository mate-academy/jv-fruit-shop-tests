package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private Operation operation;
    private Fruit fruit;
    private int quantity;

    public FruitTransaction(String operation, Fruit fruit, int quantity) {
        this.operation = Operation.getOperationType(operation);
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public Operation getOperationType() {
        return operation;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "FruitTransaction{"
                + "operation=" + operation
                + ", fruit=" + fruit
                + ", quantity=" + quantity
                + '}';
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
        return quantity == that.quantity && operation == that.operation
                && Objects.equals(fruit, that.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, quantity);
    }

    public enum Operation {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private String operation;

        Operation(String operation) {
            this.operation = operation;
        }

        private String getOperation() {
            return operation;
        }

        private static Operation getOperationType(String operationType) {
            for (Operation operationName : values()) {
                if (operationName.getOperation().equals(operationType)) {
                    return operationName;
                }
            }
            throw new RuntimeException("There is no such operation" + operationType);
        }
    }
}
