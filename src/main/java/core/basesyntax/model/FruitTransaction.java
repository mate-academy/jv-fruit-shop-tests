package core.basesyntax.model;

public class FruitTransaction {
    private String fruitName;
    private int quantity;
    private Operation operation;

    public FruitTransaction(Operation operation, String fruitName, int fruitCount) {
        this.fruitName = fruitName;
        this.quantity = fruitCount;
        this.operation = operation;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
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

        if (quantity != that.quantity) {
            return false;
        }
        if (fruitName != null ? fruitName.equals(that.fruitName) : that.fruitName == null) {
            return operation == that.operation;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = fruitName != null ? fruitName.hashCode() : 0;
        result = 31 * result + quantity;
        result = 31 * result + (operation != null ? operation.hashCode() : 0);
        return result;
    }
}
