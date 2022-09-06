package core.basesyntax.dto;

import core.basesyntax.strategy.Strategy;
import java.util.Objects;

public class Transaction {
    private Strategy strategy;
    private String fruit;
    private int value;

    public Transaction(Strategy strategy, String fruit, int value) {
        this.strategy = strategy;
        this.fruit = fruit;
        this.value = value;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public String getFruit() {
        return fruit;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
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
        return value == that.value
                && Objects.equals(strategy, that.strategy)
                && Objects.equals(fruit, that.fruit);
    }
}
