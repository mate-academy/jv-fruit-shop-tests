package core.basesyntax.dto;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import java.util.Objects;

public class TransactionDto {
    private final Operation operation;
    private final Fruit fruit;
    private final Integer amount;

    public TransactionDto(Operation operation, Fruit fruit, Integer amount) {
        this.operation = operation;
        this.fruit = fruit;
        this.amount = amount;
    }

    public Operation getOperation() {
        return operation;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public Integer getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionDto)) {
            return false;
        }
        TransactionDto that = (TransactionDto) o;
        return operation == that.operation && Objects.equals(fruit, that.fruit)
                && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, amount);
    }

    @Override
    public String toString() {
        return "TransactionDto{"
                + "operation=" + operation
                + ", fruit=" + fruit
                + ", amount=" + amount
                + '}';
    }
}
