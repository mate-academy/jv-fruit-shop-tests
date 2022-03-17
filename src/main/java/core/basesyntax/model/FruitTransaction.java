package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private final OperationType operationType;
    private final Fruit fruit;

    public FruitTransaction(OperationType operationType, Fruit fruit) {
        this.operationType = operationType;
        if (fruit == null) {
            throw new RuntimeException("Can't create transaction with null fruit");
        }
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

    public OperationType getOperationType() {
        return operationType;
    }

    public Fruit getFruit() {
        return fruit;
    }
}
