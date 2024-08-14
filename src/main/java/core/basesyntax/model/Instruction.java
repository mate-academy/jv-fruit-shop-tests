package core.basesyntax.model;

import java.util.Objects;

public class Instruction {
    private FruitOperation operation;
    private String fruit;
    private int quantity;

    public Instruction(FruitOperation operation, String fruit, int quantity) {
        this.operation = operation;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public FruitOperation getOperation() {
        return operation;
    }

    public void setOperation(FruitOperation operation) {
        this.operation = operation;
    }

    public String getFruitName() {
        return fruit;
    }

    public void setFruitName(String fruit) {
        this.fruit = fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Instruction that = (Instruction) o;
        return quantity == that.quantity && operation
                == that.operation && Objects.equals(fruit, that.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, quantity);
    }
}
