package model;

import java.util.Objects;

public class FruitRecord {
    private final String activityType;
    private final String fruitName;
    private final int amount;

    public FruitRecord(String activityType, String fruitName, int amount) {
        this.activityType = activityType;
        this.fruitName = fruitName;
        this.amount = amount;
    }

    public String getActivityType() {
        return activityType;
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
        FruitRecord that = (FruitRecord) o;
        return amount == that.amount && activityType.equals(that.activityType)
                && fruitName.equals(that.fruitName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activityType, fruitName, amount);
    }
}
