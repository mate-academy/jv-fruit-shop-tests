package core.basesyntax.model;

public class FruitTransaction {
    private FruitTransaction.Operation operation;
    private String fruit;
    private int quantity;

    public FruitTransaction.Operation getOperation() {
        return operation;
    }

    public void setOperation(FruitTransaction.Operation operation) {
        this.operation = operation;
    }

    public String getFruit() {
        return fruit;
    }

    public void setFruit(String fruit) {
        this.fruit = fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "FruitTransaction{" + "operation=" + operation
                + ", fruit='" + fruit + '\''
                + ", quantity=" + quantity + '}';
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

        if (quantity != that.quantity) {
            return false;
        }
        if (operation != that.operation) {
            return false;
        }
        return fruit.equals(that.fruit);
    }

    @Override
    public int hashCode() {
        int result = operation.hashCode();
        result = 31 * result + fruit.hashCode();
        result = 31 * result + quantity;
        return result;
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

        public static Operation getByCode(String operation) {
            for (Operation o : Operation.values()) {
                if (o.operation.equalsIgnoreCase(operation)) {
                    return o;
                }
            }
            throw new IllegalArgumentException("Wrong operation code.");
        }
    }
}
