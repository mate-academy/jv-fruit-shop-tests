package core.basesyntax.model;

import core.basesyntax.service.operations.OperationType;
import java.util.Objects;

public class FruitRecordDto {
    private OperationType operationType;
    private String fruitName;
    private Integer fruitCount;

    public FruitRecordDto(OperationType operationType, String fruitName, Integer fruitCount) {
        this.operationType = operationType;
        this.fruitName = fruitName;
        this.fruitCount = fruitCount;
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
        return operationType == that.operationType
                && Objects.equals(fruitName, that.fruitName)
                && Objects.equals(fruitCount, that.fruitCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationType, fruitName, fruitCount);
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public String getFruitName() {
        return fruitName;
    }

    public Integer getFruitCount() {
        return fruitCount;
    }
}
