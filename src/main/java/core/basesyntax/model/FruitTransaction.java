package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private Operation operation;
    private String fruit;
    private int quantity;

    public FruitTransaction(Operation operation, String fruit, int quantity) {
        this.operation = operation;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public String getFruit() {
        return fruit;
    }

    public void setFruit(String fruit) {
        this.fruit = fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public enum Operation {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private String operationKey;

        Operation(String operationKey) {
            this.operationKey = operationKey;
        }

        public static Operation getOperationFromKey(String operationKey) {
            for (Operation operation : Operation.values()) {
                if (operationKey.equals(operation.operationKey)) {
                    return operation;
                }
            }
            throw new RuntimeException("No operations with such operationKey available");
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, quantity);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FruitTransaction transaction = (FruitTransaction) obj;
        return quantity == transaction.quantity
                && operation == this.operation
                && Objects.equals(fruit, transaction.fruit);
    }
}
