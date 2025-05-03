package core.basesyntax.model;

import java.util.Objects;

public class TransactionLine {
    private final String typeActivity;
    private final String fruitName;
    private final int quantity;

    public TransactionLine(String typeActivity, String fruitName, int quantity) {
        this.typeActivity = typeActivity;
        this.fruitName = fruitName;
        this.quantity = quantity;
    }

    public String getTypeActivity() {
        return typeActivity;
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
        TransactionLine that = (TransactionLine) o;
        return quantity == that.quantity && typeActivity.equals(that.typeActivity)
                && fruitName.equals(that.fruitName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeActivity, fruitName, quantity);
    }
}
