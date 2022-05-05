package core.basesyntax.model;

import java.util.Objects;

public class LineInformation {
    private Integer quantity;
    private String operation;
    private Fruit fruit;

    public LineInformation(String action, Fruit fruit, Integer quantity) {
        this.quantity = quantity;
        this.operation = action;
        this.fruit = fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LineInformation that = (LineInformation) o;
        return Objects.equals(quantity, that.quantity)
                && Objects.equals(operation, that.operation)
                && Objects.equals(fruit, that.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, operation, fruit);
    }
}
