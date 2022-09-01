package model;

import java.util.Objects;

public class FruitTransaction {
    private Operation operationType;
    private Fruit fruit;
    private int quantity;

    public FruitTransaction(String operationType, Fruit fruit, int quantity) {
        this.operationType = Operation.getByValue(operationType);
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public Operation getOperationType() {

        return operationType;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public int getQuantity() {

        return quantity;
    }

    @Override
    public String toString() {
        return "FruitTransaction{"
                + "operationType=" + operationType
                + ", fruit=" + fruit
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
        FruitTransaction that = (FruitTransaction) o;
        return quantity == that.quantity && operationType == that.operationType
                && Objects.equals(fruit, that.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationType, fruit, quantity);
    }
}
