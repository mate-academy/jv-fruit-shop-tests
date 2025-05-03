package core.basesyntax.model;

import java.util.Objects;

public class Transaction {
    private final Operation operation;
    private final String nameFruit;
    private final int amount;

    public Transaction(Operation operation, String fruit, int quantity) {
        this.operation = operation;
        this.nameFruit = fruit;
        this.amount = quantity;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getNameFruit() {
        return nameFruit;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction)) {
            return false;
        }
        Transaction that = (Transaction) o;
        return getAmount() == that.getAmount()
                && getOperation() == that.getOperation()
                && Objects.equals(getNameFruit(), that.getNameFruit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOperation(), getNameFruit(), getAmount());
    }

    public enum Operation {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private final String operation;

        Operation(String operation) {
            this.operation = operation;
        }

        public String getOperation() {
            return operation;
        }
    }

    public static Operation findOperationByName(String value) {
        for (Operation operation : Operation.values()) {
            if (operation.getOperation().equals(value)) {
                return operation;
            }
        }
        throw new RuntimeException("Not valid operation type: " + value);
    }
}
