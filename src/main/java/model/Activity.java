package model;

import java.util.Objects;

public class Activity {
    private final ActivityType type;
    private final String fruit;
    private final int quantity;

    public Activity(ActivityType type, String fruit, int quantity) {
        this.type = Objects.requireNonNull(type);
        this.fruit = Objects.requireNonNull(fruit);
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getFruit() {
        return fruit;
    }

    public ActivityType getType() {
        return type;
    }
}
