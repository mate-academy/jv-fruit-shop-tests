package core.basesyntax.dto;

import core.basesyntax.service.OperationType;
import java.util.Objects;

public class FruitRecordDto {
    private OperationType operationType;
    private String fruitName;
    private int quantity;

    public FruitRecordDto(OperationType operation, String fruit, int quantity) {
        this.operationType = operation;
        this.fruitName = fruit;
        this.quantity = quantity;
    }

    public OperationType getOperationType() {
        return operationType;
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
                && operationType == that.operationType
                && Objects.equals(fruitName, that.fruitName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationType, fruitName, quantity);
    }
}
