package core.basesyntax.model.dto;

import core.basesyntax.service.impl.OperationType;
import java.util.Objects;

public class FruitRecordDto {
    private String fruitName;
    private OperationType operationType;
    private int quantity;

    public FruitRecordDto(OperationType operationType, String fruitName, int quantity) {
        this.operationType = operationType;
        this.fruitName = fruitName;
        this.quantity = quantity;
    }

    public String getFruitName() {
        return fruitName;
    }

    public OperationType getOperationType() {
        return operationType;
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
        return quantity == that.quantity && Objects.equals(fruitName, that.fruitName)
                && operationType == that.operationType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruitName, operationType, quantity);
    }
}
