package core.basesyntax.model;

import java.util.Objects;

public class FruitRecord {
    private final Operation typeOperation;
    private final Fruit fruit;
    private final int amount;

    public FruitRecord(String typeOperation, Fruit fruit, int amount) {
        this.typeOperation = Operation.get(typeOperation);
        this.fruit = fruit;
        this.amount = amount;
    }

    public Operation getTypeOperation() {
        return typeOperation;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public int getAmount() {
        return amount;
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
        return amount == that.amount && typeOperation == that.typeOperation
                && Objects.equals(fruit, that.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeOperation, fruit, amount);
    }
}
