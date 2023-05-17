package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private Operation operation;
    private String fruit;
    private int quantity;

    public FruitTransaction(Operation operation, String fruit, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be positive, but was " + quantity);
        }
        if (fruit == null || fruit.isBlank()) {
            throw new IllegalArgumentException("Fruit must be not null or empty, but was \""
                    + fruit + "\"");
        }
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FruitTransaction other = (FruitTransaction) obj;
        return operation == other.operation
                && Objects.equals(fruit, other.fruit)
                && quantity == other.quantity;
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

        private String code;

        Operation(String code) {
            this.code = code;
        }

        public static Operation getOperationByCode(String code) {
            for (Operation operation: Operation.values()) {
                if (code.equals(operation.getCode())) {
                    return operation;
                }
            }
            throw new IllegalArgumentException("Invalid opcode: " + code);
        }

        public String getCode() {
            return code;
        }
    }
}
