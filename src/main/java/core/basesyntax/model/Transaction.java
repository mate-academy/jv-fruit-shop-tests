package core.basesyntax.model;

import java.util.Objects;

public class Transaction {
    private String operation;
    private String fruitName;
    private int quantity;

    public Transaction(String operation, String fruitName, int quality) {
        this.operation = operation;
        this.fruitName = fruitName;
        this.quantity = quality;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getFruitName() {
        return fruitName;
    }

    public String getOperation() {
        return operation;
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
        return quantity == that.quantity
                && Objects.equals(operation, that.operation)
                && Objects.equals(fruitName, that.fruitName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruitName, quantity);
    }
}
