package core.basesyntax.model;

import java.util.Arrays;
import java.util.Objects;

public class FruitTransaction {
    private final Operation operation;

    private final String fruit;
    private final int quantity;

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

        public static Operation getOperation(String operation) {
            return Arrays.stream(Operation.values())
                    .filter(v -> v.getCode().equals(operation))
                    .findFirst()
                    .get();
        }

    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FruitTransaction)) {
            return false;
        }
        FruitTransaction that = (FruitTransaction) o;
        return quantity == that.quantity
                && operation == that.operation
                && Objects.equals(fruit, that.fruit);
    }
}
