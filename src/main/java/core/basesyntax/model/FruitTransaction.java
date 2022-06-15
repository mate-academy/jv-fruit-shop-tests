package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private String operation;
    private String fruitName;
    private int quantity;

    public FruitTransaction(String operation, String fruitName, int quantity) {
        this.operation = operation;
        this.fruitName = fruitName;
        this.quantity = quantity;
    }

    public String getOperation() {
        return operation;
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
        if (!(o instanceof FruitTransaction)) {
            return false;
        }
        FruitTransaction that = (FruitTransaction) o;
        return getQuantity() == that.getQuantity()
                && getOperation().equals(that.getOperation())
                && getFruitName().equals(that.getFruitName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOperation(), getFruitName(), getQuantity());
    }
}
