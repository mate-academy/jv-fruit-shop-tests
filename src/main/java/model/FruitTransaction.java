package model;

import java.util.Objects;

public class FruitTransaction {
    private final FruitOperationType fruitOperationType;
    private final String fruitName;
    private Integer value;

    public FruitTransaction(FruitOperationType fruitOperationType,
                            String fruitName, Integer value) {
        this.fruitOperationType = fruitOperationType;
        this.fruitName = fruitName;
        this.value = value;
    }

    public String getFruitType() {
        return fruitName;
    }

    public FruitOperationType getOperationType() {
        return fruitOperationType;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FruitTransaction that = (FruitTransaction) o;

        if (fruitOperationType != that.fruitOperationType) {
            return false;
        }
        if (!Objects.equals(fruitName, that.fruitName)) {
            return false;
        }
        return Objects.equals(value, that.value);
    }
}
