package core.basesyntax.fruitshop.model;

import java.util.Objects;

public class RecordDto {
    private OperationType operationType;
    private Fruit fruitType;
    private int amount;

    public RecordDto(OperationType operationType, Fruit fruitType, int amount) {
        this.operationType = operationType;
        this.fruitType = fruitType;
        this.amount = amount;
    }

    public RecordDto() {

    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public Fruit getFruitType() {
        return fruitType;
    }

    public void setFruitType(Fruit fruitType) {
        this.fruitType = fruitType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RecordDto dto = (RecordDto) o;
        return amount == dto.amount && operationType == dto.operationType
                && Objects.equals(fruitType, dto.fruitType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationType, fruitType, amount);
    }
}
