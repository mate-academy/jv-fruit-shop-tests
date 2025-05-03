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

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (operation == null ? 0 : operation.hashCode());
        result = 31 * result + (fruit == null ? 0 : fruit.hashCode());
        result = 31 * result + quantity;
        return result;
    }

    @Override
    public boolean equals(Object fruitTransaction) {
        if (fruitTransaction == this) {
            return true;
        }
        if (fruitTransaction == null) {
            return false;
        }
        if (fruitTransaction instanceof FruitTransaction) {
            FruitTransaction current = (FruitTransaction) fruitTransaction;
            return Objects.equals(this.operation, current.operation)
                    && Objects.equals(this.fruit, current.fruit)
                    && this.quantity == current.quantity;
        }
        return false;
    }

    public String getFruit() {
        return fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public Operation getOperation() {
        return operation;
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

        public String getOperation() {
            return operation;
        }

        public static Operation getOperationByValue(String operation) {

            for (Operation oneOperation:Operation.values()) {
                if (oneOperation.getOperation().equals(operation)) {
                    return oneOperation;
                }
            }
            throw new RuntimeException("Can't find enum value:" + operation);
        }
    }
}
