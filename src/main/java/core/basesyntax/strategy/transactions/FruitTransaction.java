package core.basesyntax.strategy.transactions;

import core.basesyntax.strategy.Operation;

import java.util.Objects;

public class FruitTransaction {
    private Operation operation;
    private String fruitName;
    private Integer valueOfFruit;

    public FruitTransaction() {
    }

    public Operation getOperation() {
        return operation;
    }

    public String getFruitName() {
        return fruitName;
    }

    public Integer getValueOfFruit() {
        return valueOfFruit;
    }

    public FruitTransaction setOperation(Operation operation) {
        this.operation = operation;
        return this;
    }

    public FruitTransaction setFruitName(String fruitName) {
        this.fruitName = fruitName;
        return this;
    }

    public FruitTransaction setValueOfFruit(Integer valueOfFruit) {
        this.valueOfFruit = valueOfFruit;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FruitTransaction that = (FruitTransaction) o;
        return operation == that.operation && Objects.equals(fruitName, that.fruitName) && Objects.equals(valueOfFruit, that.valueOfFruit);
    }
}
