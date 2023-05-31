package core.basesyntax.model;

import java.util.Objects;

public class FruitTransition {
    private String operation;
    private Fruit fruit;
    private Integer count;

    public FruitTransition(String operation, Fruit fruit, Integer count) {
        this.operation = operation;
        this.fruit = fruit;
        this.count = count;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "FruitTransition{"
                + "operation='" + operation + '\''
                + ", fruit=" + fruit
                + ", count=" + count
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FruitTransition that = (FruitTransition) o;
        return Objects.equals(operation, that.operation)
                && Objects.equals(fruit, that.fruit)
                && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, count);
    }
}
