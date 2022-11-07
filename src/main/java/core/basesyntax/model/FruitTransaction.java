package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private Fruit fruit;
    private Operation operation;
    private int quantity;


    public static FruitTransaction of(Operation operation, Fruit fruit, int quantity) {
        return new FruitTransaction(operation, fruit, quantity);
    }

    public FruitTransaction(Operation operation, Fruit fruit, int quantity) {
    }

    public FruitTransaction() {}

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
        return quantity == that.quantity && Objects.equals(fruit, that.fruit)
                && operation == that.operation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruit, operation, quantity);
    }

    @Override
    public String toString() {
        return "FruitTransaction{"
                + "operation="
                + operation
                + ", fruit='"
                + fruit
                + '\''
                + ", amount="
                + quantity
                + '}';
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
