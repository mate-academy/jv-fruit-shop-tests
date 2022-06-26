package core.basesyntax.model;

import java.util.Arrays;

public class FruitTransaction {
    private Operation operation;
    private String fruit;
    private int quantity;

    public FruitTransaction() {
    }

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

    public Operation setValueForOperation(String operationName) {
        return Arrays.stream(Operation.values())
                .filter(o -> o.getOperation().equals(operationName))
                .findFirst()
                .orElseThrow();
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

        private final String operation;

        Operation(String operation) {
            this.operation = operation;
        }

        public String getOperation() {
            return operation;
        }
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
        if (fruitTransaction == null) {
            return false;
        }

        if (this == fruitTransaction) {
            return true;
        }

        if (fruitTransaction.getClass().equals(FruitTransaction.class)) {
            FruitTransaction current = (FruitTransaction) fruitTransaction;
            return this.operation != null && this.operation.equals(current.operation)
                    && this.fruit != null && this.fruit.equals(current.fruit)
                    && this.quantity == current.quantity;
        }
        return false;
    }
}
