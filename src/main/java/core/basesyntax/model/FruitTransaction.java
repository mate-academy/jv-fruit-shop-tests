package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {

    private TypeOfTransaction typeOfTransaction;
    private String fruit;
    private Integer quantity;

    public String getFruit() {
        return fruit;
    }

    public TypeOfTransaction getTypeOfTransaction() {
        return typeOfTransaction;
    }

    public void setTypeOfTransaction(TypeOfTransaction typeOfTransaction) {
        try {
            this.typeOfTransaction = typeOfTransaction;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Type of transaction is incorrect.", e);
        }
    }

    public void setFruit(String fruit) {
        if (!(fruit == null) && !fruit.isEmpty()) {
            this.fruit = fruit.toLowerCase();
        } else {
            throw new RuntimeException("Name of fruit is null or empty.");
        }
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        if (quantity != null && quantity >= 0) {
            this.quantity = quantity;
        } else {
            throw new RuntimeException("Quantity has incorrect value.");
        }
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
        return typeOfTransaction == that.typeOfTransaction
                && Objects.equals(fruit, that.fruit)
                && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeOfTransaction, fruit, quantity);
    }

    @Override
    public String toString() {
        return "FruitTransaction{"
                + "fruit='" + fruit + '\''
                + ", quantity=" + quantity
                + '}';
    }
}
