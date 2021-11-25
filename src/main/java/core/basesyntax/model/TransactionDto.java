package core.basesyntax.model;

import java.util.Objects;

public class TransactionDto {
    private OperationType operationType;
    private Fruit fruit;
    private Integer fruitAmount;

    public TransactionDto(OperationType operationType, Fruit fruit, Integer fruitAmount) {
        this.operationType = operationType;
        this.fruit = fruit;
        this.fruitAmount = fruitAmount;
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
        return operationType == that.operationType
                && Objects.equals(fruit, that.fruit)
                && Objects.equals(fruitAmount, that.fruitAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationType, fruit, fruitAmount);
    }

    @Override
    public String toString() {
        return "operationType=" + operationType
                + ", fruitName=" + fruit.getFruitName()
                + ", fruitAmount=" + fruitAmount;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
    }

    public Integer getFruitAmount() {
        return fruitAmount;
    }

    public void setFruitAmount(Integer fruitAmount) {
        this.fruitAmount = fruitAmount;
    }
}
