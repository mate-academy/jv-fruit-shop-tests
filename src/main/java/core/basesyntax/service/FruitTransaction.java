package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import core.basesyntax.operations.Operation;
import java.util.Objects;

public class FruitTransaction {
    private Operation operationType;
    private Fruit fruit;

    public FruitTransaction(Operation operationType, Fruit fruit) {
        this.operationType = operationType;
        this.fruit = fruit;
    }

    public Operation getOperation() {
        return operationType;
    }

    public void setOperation(Operation operation) {
        this.operationType = operation;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
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
        return operationType == that.operationType && Objects.equals(fruit, that.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationType, fruit);
    }
}
