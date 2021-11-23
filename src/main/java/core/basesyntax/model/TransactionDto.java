package core.basesyntax.model;

import java.util.Objects;

public class TransactionDto {
    private String activity;
    private String fruitName;
    private int quantity;

    public TransactionDto(String activity, String fruitName, int quantity) {
        this.activity = activity;
        this.fruitName = fruitName;
        this.quantity = quantity;
    }

    public String getActivity() {
        return activity;
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
        TransactionDto that = (TransactionDto) o;
        return quantity == that.quantity && Objects.equals(activity, that.activity)
                && Objects.equals(fruitName, that.fruitName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activity, fruitName, quantity);
    }
}
