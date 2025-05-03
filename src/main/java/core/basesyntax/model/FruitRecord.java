package core.basesyntax.model;

import java.util.Objects;

public class FruitRecord {
    private String operationType;
    private String fruitName;
    private Integer amount;

    public FruitRecord(String operationType, String fruitName, Integer amount) {
        this.operationType = operationType;
        this.fruitName = fruitName;
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getFruitName() {
        return fruitName;
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
        FruitRecord fruitRecord = (FruitRecord) o;
        return Objects.equals(operationType, fruitRecord.operationType)
                && Objects.equals(fruitName, fruitRecord.fruitName)
                && Objects.equals(amount, fruitRecord.amount);
    }
}
