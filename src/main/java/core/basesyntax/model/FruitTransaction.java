package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private final Operation operation;
    private final String fruit;
    private final Integer quantity;

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

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 31 + operation.hashCode();
        hash = hash * 31 + fruit.hashCode();
        hash = hash * 31 + quantity.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass().equals(FruitTransaction.class)) {
            FruitTransaction transaction = (FruitTransaction) obj;
            return (Objects.equals(this.operation, transaction.operation)
                    && Objects.equals(this.fruit, transaction.fruit)
                    && Objects.equals(this.quantity, transaction.quantity));
        }
        return false;
    }

    @Override
    public String toString() {
        return operation.name() + ", " + fruit + ", " + quantity;
    }

    public enum Operation {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private final String stringValue;

        Operation(String stringValue) {
            this.stringValue = stringValue;
        }

        public String getOperation() {
            return stringValue;
        }

        public static Operation getOperationFromString(String sign) {
            for (Operation value : Operation.values()) {
                if (value.stringValue.equalsIgnoreCase(sign)) {
                    return value;
                }
            }
            throw new RuntimeException("Wrong operation sign " + sign);
        }
    }
}
