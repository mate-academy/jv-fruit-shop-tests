package core.basesyntax.model;

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

    @Override
    public int hashCode() {
        int result = operation != null ? operation.hashCode() : 0;
        result = 31 * result + (fruit != null ? fruit.hashCode() : 0);
        result = 31 * result + quantity;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !this.getClass().equals(obj.getClass())) {
            return false;
        }
        FruitTransaction transaction = (FruitTransaction) obj;
        return this.operation != null && this.operation == transaction.operation
                && this.fruit != null && this.fruit.equals(transaction.fruit)
                && this.quantity == transaction.quantity;
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
}
