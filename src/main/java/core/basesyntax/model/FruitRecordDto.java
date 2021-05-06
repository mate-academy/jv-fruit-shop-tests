package core.basesyntax.model;

import java.util.Objects;

public class FruitRecordDto {
    private String operationType;
    private String fruitType;
    private int amount;

    public FruitRecordDto(String operationType, String fruitType, int amount) {
        this.operationType = operationType;
        this.fruitType = fruitType;
        this.amount = amount;
    }

    public String getFruitType() {
        return fruitType;
    }

    public int getAmount() {
        return amount;
    }

    public String getOperationType() {
        return operationType;
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
        return amount == that.amount
                && Objects.equals(operationType, that.operationType)
                && Objects.equals(fruitType, that.fruitType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationType, fruitType, amount);
    }
}
