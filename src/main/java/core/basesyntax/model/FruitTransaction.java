package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {

    private int quantity;
    private String fruit;
    private Operation operation;

    public FruitTransaction(Integer quantity, String fruit, Operation operation) {
        if (quantity == null && quantity < 0) {
            throw new NullPointerException("operation cannot be null");
        }
        this.quantity = quantity;
        if (fruit == null) {
            throw new NullPointerException("fruit cannot be null");
        }
        this.fruit = fruit;
        this.operation = operation;
    }

    public String getFruit() {
        return fruit;
    }

    public Operation getOperation() {
        return operation;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setFruit(String fruit) {
        this.fruit = fruit;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public enum Operation {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private String code;
        Operation(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FruitTransaction that = (FruitTransaction) o;
        return quantity == that.quantity && Objects.equals(fruit, that.fruit)
                && operation == that.operation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, fruit, operation);
    }
}
