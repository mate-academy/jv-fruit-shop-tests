package core.basesyntax.model;

public class TransactionDto {
    private Operation operation;
    private String fruitName;
    private int quantity;

    public TransactionDto(Operation operation, String fruitName, int quantity) {
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

        if (quantity != that.quantity) {
            return false;
        }
        if (operation != that.operation) {
            return false;
        }
        return fruitName.equals(that.fruitName);
    }

    @Override
    public int hashCode() {
        int result = operation.hashCode();
        result = 31 * result + fruitName.hashCode();
        result = 31 * result + quantity;
        return result;
    }
}
