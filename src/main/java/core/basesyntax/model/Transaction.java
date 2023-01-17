package core.basesyntax.model;

import java.util.Objects;

public class Transaction {
    private Operation operation;
    private Fruit fruit;
    private Integer sum;

    public Transaction() {
    }

    public Transaction(Operation operation, Fruit fruit, Integer sum) {
        this.operation = operation;
        this.fruit = fruit;
        this.sum = sum;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transaction that = (Transaction) o;
        return operation == that.operation
                && Objects.equals(fruit, that.fruit)
                && Objects.equals(sum, that.sum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, sum);
    }

    @Override
    public String toString() {
        return "Transaction{"
                + "operation=" + operation
                + ", fruit=" + fruit
                + ", sum=" + sum
                + '}';
    }
}
