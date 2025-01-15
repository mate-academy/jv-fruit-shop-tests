package core.basesyntax.store.model;

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

    public String getFruit() {
        return fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public enum Operation {
        BALANCE("b"), // BALANCE - operation to set the initial stock balance
        SUPPLY("s"), // SUPPLY - operation to add stock to the inventory
        PURCHASE("p"), // PURCHASE - operation to reduce stock when a purchase is made
        RETURN("r"); // RETURN - operation to add stock back when items are returned
        private final String code;

        Operation(String code) {
            this.code = code;
        }

        public static Operation fromCode(String code) {
            for (Operation operation : values()) {
                if (operation.code.equals(code)) {
                    return operation;
                }
            }
            throw new IllegalArgumentException("Invalid operation code: " + code);
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
        return quantity == that.quantity && operation
                == that.operation && Objects.equals(fruit, that.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, quantity);
    }
}
