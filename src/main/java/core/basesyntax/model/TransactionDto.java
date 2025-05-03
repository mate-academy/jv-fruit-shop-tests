package core.basesyntax.model;

import core.basesyntax.strategy.OperationType;
import java.util.Objects;

public class TransactionDto {
    private OperationType operation;
    private String fruitName;
    private int quantity;

    public TransactionDto(OperationType operation, String fruitName, int quantiti) {
        this.operation = operation;
        this.fruitName = fruitName;
        this.quantity = quantiti;
    }

    public OperationType getOperation() {
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
        return quantity == that.quantity && operation == that.operation
                && Objects.equals(fruitName, that.fruitName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruitName, quantity);
    }
}
