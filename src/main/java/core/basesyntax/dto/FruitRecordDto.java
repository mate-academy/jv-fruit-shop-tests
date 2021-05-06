package core.basesyntax.dto;

import core.basesyntax.model.Operation;
import java.util.Objects;

public class FruitRecordDto {
    private Operation operationType;
    private String fruitName;
    private Integer quantity;

    public FruitRecordDto(Operation operationType, String fruitName, Integer quantity) {
        this.operationType = operationType;
        this.fruitName = fruitName;
        this.quantity = quantity;
    }

    public Operation getOperationType() {
        return operationType;
    }

    public String getFruitName() {
        return fruitName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FruitRecordDto)) {
            return false;
        }
        FruitRecordDto recordDto = (FruitRecordDto) o;
        return getOperationType() == recordDto.getOperationType() && Objects.equals(getFruitName(),
                recordDto.getFruitName()) && Objects.equals(getQuantity(), recordDto.getQuantity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOperationType(), getFruitName(), getQuantity());
    }
}
