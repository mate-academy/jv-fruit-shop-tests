package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private String fruitOperation;
    private Fruit fruit;
    private Integer amount;

    public FruitTransaction(String fruitOperation, Fruit fruit, Integer amount) {
        this.fruitOperation = fruitOperation;
        this.fruit = fruit;
        this.amount = amount;
    }

    public String getOperation() {
        return fruitOperation;
    }

    public void setOperation(String fruitOperation) {
        this.fruitOperation = fruitOperation;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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
        return Objects.equals(fruitOperation, that.fruitOperation)
                && Objects.equals(fruit, that.fruit)
                && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruitOperation, fruit, amount);
    }

    @Override
    public String toString() {
        return "FruitTransaction{"
                + "fruitOperation='" + fruitOperation + '\''
                + ", fruit=" + fruit
                + ", amount=" + amount
                + '}';
    }
}
