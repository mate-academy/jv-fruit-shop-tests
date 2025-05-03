package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private Operation operation;
    private String fruit;
    private int quantity;

    public String getFruit() {
        return fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public Operation getOperation() {
        return this.operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public void setFruit(String fruit) {
        this.fruit = fruit;
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

        Operation(String operation) {
            this.code = operation;
        }

        public static Operation getByCode(String code) {
            for (Operation o : Operation.values()) {
                if (code.equals(o.getOperation())) {
                    return o;
                }
            }
            return null;
        }

        public String getOperation() {
            return code;
        }
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
        return quantity == that.quantity && operation == that.operation && fruit.equals(that.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, quantity);
    }
}
