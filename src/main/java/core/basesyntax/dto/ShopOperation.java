package core.basesyntax.dto;

import java.util.Objects;

public class ShopOperation {
    private String operation;
    private String fruitName;
    private int quantity;

    public ShopOperation(String operation, String fruitName, int quantity) {
        this.operation = operation;
        this.fruitName = fruitName;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShopOperation that = (ShopOperation) o;
        return quantity == that.quantity && Objects.equals(operation, that.operation)
                && Objects.equals(fruitName, that.fruitName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruitName, quantity);
    }

    @Override
    public String toString() {
        return "ShopOperation{"
                + "operation='" + operation + '\''
                + ", fruitName='" + fruitName + '\''
                + ", quantity=" + quantity + '}';
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

}
