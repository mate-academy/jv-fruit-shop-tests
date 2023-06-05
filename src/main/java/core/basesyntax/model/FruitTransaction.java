package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private Operation operation;
    private String fruit;
    private int quantity;

    public FruitTransaction(Operation operation, String fruit, int quantity) {
        if (operation == null) {
            throw new IllegalArgumentException("Operation cannot be null");
        }
        if (fruit == null) {
            throw new IllegalArgumentException("Fruit cannot be null");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative: " + quantity);
        }
        this.operation = operation;
        this.fruit = fruit;
        this.quantity = quantity;
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
        if (quantity != that.quantity) {
            return false;
        }
        if (operation != that.operation) {
            return false;
        }
        return Objects.equals(fruit, that.fruit);
    }

    @Override
    public int hashCode() {
        int result = operation != null ? operation.hashCode() : 0;
        result = 31 * result + (fruit != null ? fruit.hashCode() : 0);
        result = 31 * result + quantity;
        return result;
    }

    @Override
    public String toString() {
        return "FruitTransaction{"
                + "operation=" + operation
                + ", fruit='" + fruit + '\''
                + ", quantity=" + quantity
                + '}';
    }

    public Operation getOperation() {
        return operation;
    }

    public String getFruit() {
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

        private String code;

        Operation(String code) {
            this.code = code;
        }

        public static Operation findOperation(String code) throws IllegalArgumentException {
            for (Operation operation: Operation.values()) {
                if (code.equals(operation.code)) {
                    return operation;
                }
            }
            throw new IllegalArgumentException("Operation cannot be null");
        }
    }
}
