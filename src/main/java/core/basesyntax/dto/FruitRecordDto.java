package core.basesyntax.dto;

import core.basesyntax.service.OperationType;
import java.util.Objects;

public class FruitRecordDto {
    private OperationType operationType;
    private String fruitName;
    private Integer quantity;

    public FruitRecordDto(OperationType operationType, String fruitName, Integer quantity) {
        this.operationType = operationType;
        this.fruitName = fruitName;
        this.quantity = quantity;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public String getFruitName() {
        return fruitName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationType, fruitName, quantity);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FruitRecordDto that = (FruitRecordDto) obj;
        return quantity == that.quantity && Objects
                .equals(fruitName, that.fruitName) && operationType == that.operationType;
    }
}
