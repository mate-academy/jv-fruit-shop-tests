package model.dto;

import java.util.Objects;
import model.OperationType;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FruitRecordDto fruitRecordDto = (FruitRecordDto) obj;
        return operationType == fruitRecordDto.operationType
                && Objects.equals(fruitName, fruitRecordDto.fruitName)
                && Objects.equals(quantity, fruitRecordDto.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationType, fruitName, quantity);
    }

    public String getFruitName() {
        return fruitName;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
