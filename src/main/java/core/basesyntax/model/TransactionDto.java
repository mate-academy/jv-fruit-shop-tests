package core.basesyntax.model;

import java.util.Objects;

public class TransactionDto {
    private String manipulation;
    private String fruitName;
    private int quantity;

    public TransactionDto(String manipulation, String fruitName, int quantity) {
        this.manipulation = manipulation;
        this.fruitName = fruitName;
        this.quantity = quantity;
    }

    public String getManipulation() {
        return manipulation;
    }

    public void setManipulation(String manipulation) {
        this.manipulation = manipulation;
    }

    public String getFruitName() {
        return fruitName;
    }

    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionDto)) {
            return false;
        }
        TransactionDto that = (TransactionDto) o;
        return quantity == that.quantity && Objects.equals(manipulation, that.manipulation)
                && Objects.equals(fruitName, that.fruitName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(manipulation, fruitName, quantity);
    }

    @Override
    public String toString() {
        return "TransactionDto{"
                + "manipulation='" + manipulation + '\''
                + ", fruitName='" + fruitName + '\''
                + ", quantity=" + quantity
                + '}';
    }
}
