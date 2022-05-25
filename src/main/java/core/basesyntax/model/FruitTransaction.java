package core.basesyntax.model;

import java.util.NoSuchElementException;
import java.util.Objects;

public class FruitTransaction {
    private Operation operation;
    private String fruit;
    private int quantity;

    public void setFruit(String fruit) {
        this.fruit = fruit;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getFruit() {
        return fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Operation getOperation() {
        return operation;
    }

    public enum Operation {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private String abbreviature;

        Operation(String operation) {
            this.abbreviature = operation;
        }

        public String getAbbreviature() {
            return abbreviature;
        }

        public static Operation getOperation(String abbreviature) {
            for (Operation operation : Operation.values()) {
                if (operation.getAbbreviature().equals(abbreviature)) {
                    return operation;
                }
            }
            throw new NoSuchElementException("Can`t find such Operation" + abbreviature);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass() != getClass()) {
            return false;
        }
        FruitTransaction transaction = (FruitTransaction) o;
        return Objects.equals(transaction.fruit, fruit)
                && Objects.equals(transaction.operation, operation)
                && transaction.quantity == quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruit, operation, quantity);
    }
}

