package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private FruitOperation fruitOperation;
    private String fruitName;
    private int quantity;

    public FruitTransaction(FruitOperation fruitOperation, String fruitName, int quantity) {
        this.fruitOperation = fruitOperation;
        this.fruitName = fruitName;
        this.quantity = quantity;
    }

    public FruitOperation getFruitOperation() {
        return fruitOperation;
    }

    public String getFruitName() {
        return fruitName;
    }

    public int getQuantity() {
        return quantity;
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
        return quantity == that.quantity && fruitOperation == that.fruitOperation
                && Objects.equals(fruitName, that.fruitName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruitOperation, fruitName, quantity);
    }
}
