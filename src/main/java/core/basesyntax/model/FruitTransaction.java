package core.basesyntax.model;

public class FruitTransaction {
    private Operation operation;
    private String fruitName;
    private Integer quantity;

    public FruitTransaction(Operation operation, String fruitName, int quantity) {
        this.operation = operation;
        this.fruitName = fruitName;
        this.quantity = quantity;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getFruitName() {
        return fruitName;
    }

    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
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

        if (!operation.equals(that.operation)) {
            return false;
        }
        if (!fruitName.equals(that.fruitName)) {
            return false;
        }
        return quantity.equals(that.quantity);
    }

    @Override
    public int hashCode() {
        int result = operation.hashCode();
        result = 31 * result + fruitName.hashCode();
        result = 31 * result + quantity.hashCode();
        return result;
    }
}
