package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private Operation operation;
    private Fruit fruit;
    private int quantity;

    public FruitTransaction(Operation operation, Fruit fruit, int quantity) {
        this.operation = operation;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public Operation getOperation() {
        return operation;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null) {
            return false;
        }

        if (object.getClass() != FruitTransaction.class) {
            return false;
        }

        FruitTransaction transaction = (FruitTransaction) object;

        return Objects.equals(this.operation, transaction.operation)
                && Objects.equals(this.fruit, transaction.fruit)
                && this.quantity == transaction.quantity;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public enum Operation {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private final String alias;

        Operation(String alias) {
            this.alias = alias;
        }

        public static Operation getOperationByAlias(String alias) {
            for (Operation operation : Operation.values()) {
                if (operation.alias.equalsIgnoreCase(alias)) {
                    return operation;
                }
            }
            throw new RuntimeException("No operations for alias " + alias);
        }
    }
}
