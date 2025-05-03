package core.basesyntax.model;

import java.util.Objects;
import java.util.stream.Stream;

public class FruitTransaction {
    private Operation operation;
    private String fruit;
    private int quantity;

    public FruitTransaction(String operation, String fruit, int quantity) {
        this.operation = getOperationValue(operation);
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

    public Operation getOperationValue(String stringValue) {
        return Stream.of(Operation.values())
                .filter(e -> e.operation.equals(stringValue)).findFirst().orElse(null);
    }

    @Override
    public boolean equals(Object fruitTransaction) {
        if (fruitTransaction == this) {
            return true;
        }

        if (fruitTransaction == null) {
            return false;
        }

        if (fruitTransaction.getClass().equals(FruitTransaction.class)) {
            FruitTransaction current = (FruitTransaction) fruitTransaction;

            try {
                return ((this.operation == null & current.operation == null)
                        || (this.operation.equals(current.operation)))
                        & ((this.fruit == null & current.fruit == null)
                        || (this.fruit.equals(current.fruit)))
                        & this.quantity == current.quantity;
            } catch (NullPointerException e) {
                return false;
            }
        }
        return false;
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

        private String operation;

        Operation(String operation) {
            this.operation = operation;
        }

        public String getOperation() {
            return operation;
        }
    }
}
