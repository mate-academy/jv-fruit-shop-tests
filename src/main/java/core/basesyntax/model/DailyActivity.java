package core.basesyntax.model;

import java.util.Objects;

public class DailyActivity {
    private Integer amount;
    private String activityType;
    private String fruitName;

    public DailyActivity(String activityType, String fruitName, Integer amount) {
        this.activityType = activityType;
        this.fruitName = fruitName;
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getActivityType() {
        return activityType;
    }

    public String getFruitName() {
        return fruitName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DailyActivity that = (DailyActivity) o;
        return Objects.equals(amount, that.amount)
                && Objects.equals(activityType, that.activityType)
                && Objects.equals(fruitName, that.fruitName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, activityType, fruitName);
    }

    @Override
    public String toString() {
        return "DailyActivity{"
                + "amount=" + amount
                + ", activityType='" + activityType + '\''
                + ", fruitName='" + fruitName + '\'' + '}';
    }
}
