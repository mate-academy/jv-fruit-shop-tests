package model;

import java.util.Objects;

public class FruitTransaction {
    public static final int MIN_QUANTITY = 0;
    private Operation operation;
    private String fruit;
    private int quantity;

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

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        FruitTransaction fruitTransaction = (FruitTransaction) object;
        return Objects.equals(quantity, fruitTransaction.quantity)
                && Objects.equals(fruit, fruitTransaction.fruit)
                && operation == fruitTransaction.operation;
    }

    @Override
    public int hashCode() {
        int resultHash = 17;
        resultHash = 31 * quantity;
        resultHash = 31 * resultHash + (fruit == null ? 0 : fruit.hashCode());
        resultHash = 31 * resultHash + (operation == null ? 0 : operation.hashCode());
        return resultHash;
    }

    @Override
    public String toString() {
        return "FruitTransaction{"
                + "operation=" + operation
                + ", fruit='" + fruit
                + ", quantity=" + quantity
                + '}';
    }

    public enum Operation {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private String code;

        Operation(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public static FruitTransaction.Operation findEnumValue(String operationCode) {
            for (FruitTransaction.Operation operation : FruitTransaction.Operation.values()) {
                if (operation.getCode().equals(operationCode)) {
                    return operation;
                }
            }
            throw new IllegalArgumentException("No operations with: " + operationCode);
        }
    }
}
