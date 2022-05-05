package core.basesyntax.model;

import java.util.Objects;

public class LineData {
    private Integer quantity;
    private String action;
    private Fruit fruit;

    public LineData(String dailyAction, Fruit fruit, Integer quantity) {
        this.action = dailyAction;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getAction() {
        return action;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LineData lineData = (LineData) o;
        return Objects.equals(quantity, lineData.quantity)
                && Objects.equals(action, lineData.action)
                && Objects.equals(fruit, lineData.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, action, fruit);
    }
}
