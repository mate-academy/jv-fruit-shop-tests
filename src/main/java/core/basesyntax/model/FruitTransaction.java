package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private final OperationType operationType;
    private final String fruitName;
    private final int amount;

    public FruitTransaction(OperationType operationType, String name, int amount) {
        this.operationType = operationType;
        this.fruitName = name;
        this.amount = amount;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public String getFruitName() {
        return fruitName;
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
        FruitTransaction that = (FruitTransaction) o;
        return amount == that.amount
                && Objects.equals(fruitName, that.fruitName)
                && operationType == that.operationType;
    }
}
