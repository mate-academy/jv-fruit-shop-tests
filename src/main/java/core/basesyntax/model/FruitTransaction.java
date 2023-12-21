package core.basesyntax.model;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class FruitTransaction {
    private Operation operation;
    private String fruit;
    private int quantity;

    public FruitTransaction(String operation, String fruit, int quantity) {
        this.operation = Operation.getOption(operation);
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

    @Override
    public boolean equals(Object transaction) {
        if (transaction == this) {
            return true;
        }

        if (!(transaction instanceof FruitTransaction)) {
            return false;
        }

        FruitTransaction current = (FruitTransaction) transaction;

        return this.operation == current.operation
                && this.fruit == null ? current.fruit == null : this.fruit.equals(current.fruit)
                && this.quantity == current.quantity;
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

        public static Operation getOption(String option) {
            return Arrays.stream(Operation.values())
                    .filter(o -> o.getCode().equals(option))
                    .findFirst().orElseThrow(NoSuchElementException::new);
        }
    }
}
