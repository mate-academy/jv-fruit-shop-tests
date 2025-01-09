package core.basesyntax.model;

import java.util.Objects;

public class ResultData {
    private String fruitName;
    private int quantity;

    public ResultData(String fruitName, int quantity) {
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResultData that = (ResultData) o;
        return quantity == that.quantity && Objects.equals(fruitName, that.fruitName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruitName, quantity);
    }
}
