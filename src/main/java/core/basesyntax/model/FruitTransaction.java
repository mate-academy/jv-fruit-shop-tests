package core.basesyntax.model;

public class FruitTransaction {
    private Operation operation;
    private String fruitName;
    private int quantity;

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
    public int hashCode() {
        int result = 17;
        result = 31 * result + (operation == null ? 0 : operation.hashCode());
        result = 31 * result + (fruitName == null ? 0 : fruitName.hashCode());
        result = 31 * result + quantity;

        return result;
    }

    @Override
    public boolean equals(Object transaction) {
        if (transaction == null) {
            return false;
        }
        if (this == transaction) {
            return true;
        }
        if (!transaction.getClass().equals(FruitTransaction.class)) {
            return false;
        }
        FruitTransaction current = (FruitTransaction) transaction;
        return (operation == current.operation)
                && (fruitName == current.fruitName
                || (fruitName != null && fruitName.equals(current.fruitName)))
                && quantity == current.quantity;
    }
}
