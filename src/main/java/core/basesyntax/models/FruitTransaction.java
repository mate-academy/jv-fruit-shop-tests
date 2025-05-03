package core.basesyntax.models;

import java.util.Objects;

public class FruitTransaction {
    private String operation;
    private String fruit;
    private int quantity;

    public FruitTransaction(String operation, String fruit, int quantity) {
        this.operation = operation;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
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
    public int hashCode() {
        return operation.hashCode() + fruit.hashCode() + quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass().equals(obj.getClass())) {
            FruitTransaction that = (FruitTransaction) obj;
            return Objects.equals(operation, that.getOperation())
                    && Objects.equals(fruit, that.getFruit())
                    && quantity == that.getQuantity();
        }
        return false;
    }
}
