package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private Operation operation;

    private Fruit fruit;

    private int quantity;

    public FruitTransaction(Operation operation, Fruit fruit, int quantity) {
        this.operation = operation;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public Operation getOperation() {
        return operation;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public enum Operation {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private String operationType;

        Operation(String operationType) {
            this.operationType = operationType;
        }

        public String getOperationType() {
            return operationType;
        }

        public static Operation getOperation(String operationCode) {
            for (Operation operation : Operation.values()) {
                if (operation.getOperationType().equals(operationCode)) {
                    return operation;
                }
            }
            throw new IllegalArgumentException("No operation was found: " + operationCode);
        }
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
        return quantity == that.quantity
                && operation == that.operation
                && Objects.equals(fruit, that.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, quantity);
    }
}
