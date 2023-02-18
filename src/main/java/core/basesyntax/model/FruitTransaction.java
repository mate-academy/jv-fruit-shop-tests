package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private ActivityType activityType;
    private Fruit fruit;
    private Integer amount;

    public FruitTransaction(ActivityType activityType, Fruit fruit, Integer amount) {
        this.activityType = activityType;
        this.fruit = fruit;
        this.amount = amount;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
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
        return activityType == that.activityType
                && Objects.equals(fruit, that.fruit)
                && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activityType, fruit, amount);
    }
}
