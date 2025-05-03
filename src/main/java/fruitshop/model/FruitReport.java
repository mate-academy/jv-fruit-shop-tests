package fruitshop.model;

import java.util.Objects;

public class FruitReport {
    private final String fruitName;
    private final int quantity;

    public FruitReport(String fruitName, int quantity) {
        this.fruitName = fruitName;
        this.quantity = quantity;
    }

    public String getFruitName() {
        return fruitName;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        FruitReport that = (FruitReport) o;
        return quantity == that.quantity && Objects.equals(fruitName, that.fruitName);
    }
}
