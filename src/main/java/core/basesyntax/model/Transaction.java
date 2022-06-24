package core.basesyntax.model;

import java.util.Objects;

public final class Transaction {
    private final Operation operation;
    private final String item;
    private final int quantity;

    public Transaction(Operation operation, String item, int quantity) {
        this.operation = operation;
        this.item = item;
        this.quantity = quantity;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Transaction{"
                + "operation=" + operation
                + ", item='" + item + '\''
                + ", quantity=" + quantity
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transaction that = (Transaction) o;
        return getQuantity() == that.getQuantity()
                && getOperation() == that.getOperation()
                && Objects.equals(getItem(), that.getItem());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOperation(), getItem(), getQuantity());
    }

    public static Operation getOperationType(String value) {
        for (Operation operation : Operation.values()) {
            if (operation.getType().equals(value)) {
                return operation;
            }
        }
        throw new RuntimeException("Unknown operation type: " + value);
    }

    public enum Operation {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private final String type;

        Operation(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
