package core.basesyntax.model.dto;

import core.basesyntax.model.Operation;
import java.util.Objects;

public class FruitRecordDto {
    private Operation operation;
    private String fruitName;
    private int quantity;

    public FruitRecordDto(Operation operation, String fruitName, int quantity) {
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
        FruitRecordDto that = (FruitRecordDto) o;
        return quantity == that.quantity
                && operation == that.operation
                && Objects.equals(fruitName, that.fruitName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruitName, quantity);
    }

    @Override
    public String toString() {
        return "FruitRecordDto{"
                + "operation=" + operation
                + ", fruitName='" + fruitName + '\''
                + ", quantity=" + quantity
                + '}';
    }
}
