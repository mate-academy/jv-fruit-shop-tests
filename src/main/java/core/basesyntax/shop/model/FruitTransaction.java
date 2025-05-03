package core.basesyntax.shop.model;

import java.util.Objects;

public class FruitTransaction {
    private OperationType operationType;
    private String fruitName;
    private int quantity;

    public FruitTransaction() {

    }

    public FruitTransaction(OperationType operationType, String fruitName, int quantity) {
        this.operationType = operationType;
        this.fruitName = fruitName;
        this.quantity = quantity;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
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
        if (!(o instanceof FruitTransaction)) {
            return false;
        }
        FruitTransaction that = (FruitTransaction) o;
        return getQuantity() == that.getQuantity()
                && getOperationType() == that.getOperationType()
                && Objects.equals(getFruitName(), that.getFruitName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOperationType(), getFruitName(), getQuantity());
    }

    @Override
    public String toString() {
        return "FruitRecord{"
                + "operationType=" + operationType
                + ", fruitName='" + fruitName + '\''
                + ", quantity=" + quantity
                + '}';
    }
}
