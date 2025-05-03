package core.basesyntax.model;

import java.util.Objects;

public class TransactionDto {
    private OperationType operationType;
    private Fruit fruit;
    private int fruitAmount;

    public TransactionDto(OperationType operationType, Fruit fruit, int fruitAmount) {
        this.operationType = operationType;
        this.fruit = fruit;
        this.fruitAmount = fruitAmount;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public Fruit getFruitType() {
        return fruit;
    }

    public int getFruitAmount() {
        return fruitAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TransactionDto that = (TransactionDto) o;
        return fruitAmount == that.fruitAmount
                && operationType == that.operationType
                && Objects.equals(fruit, that.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationType, fruit, fruitAmount);
    }
}
