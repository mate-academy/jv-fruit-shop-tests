package core.basesyntax.models;

import java.util.Objects;

public class FruitTransfer {
    private Operation action;
    private String fruit;
    private int quantity;

    public FruitTransfer() {
    }

    public FruitTransfer(Operation action, String fruit, int quantity) {
        this.action = action;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public Operation getAction() {
        return action;
    }

    public String getFruit() {
        return fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !obj.getClass().equals(FruitTransfer.class)) {
            return false;
        }
        FruitTransfer current = (FruitTransfer) obj;
        return Objects.equals(this.fruit, current.fruit)
                && Objects.equals(this.action, current.action)
                && this.quantity == current.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, fruit, quantity);
    }

    @Override
    public String toString() {
        return "FruitTransfer{"
                + "action=" + action
                + ", fruit='" + fruit + '\''
                + ", quantity=" + quantity + '}';
    }
}
