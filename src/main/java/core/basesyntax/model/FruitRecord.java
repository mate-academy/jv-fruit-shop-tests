package core.basesyntax.model;

import java.util.Objects;

public class FruitRecord {
    private final Fruit fruit;
    private final OperationType operationType;

    public FruitRecord(Fruit fruit, OperationType operationType) {
        this.fruit = fruit;
        this.operationType = operationType;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public OperationType getOperationType() {
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
        FruitRecord that = (FruitRecord) o;
        return Objects.equals(fruit, that.fruit) && operationType == that.operationType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruit, operationType);
    }

    @Override
    public String toString() {
        return "FruitRecord{"
                + "fruit="
                + fruit.toString()
                + ", operationType="
                + operationType + '}';
    }
}
