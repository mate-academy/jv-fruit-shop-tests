package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private Operation operation;
    private Fruit fruit;
    private int quantity;

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public enum Operation {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private final String code;

        Operation(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public static Operation of(String code) {
            switch (code) {
                case "b":
                    return BALANCE;
                case "s":
                    return SUPPLY;
                case "p":
                    return PURCHASE;
                case "r":
                    return RETURN;
                default:
                    throw new RuntimeException("unknown operation type");
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (!(o instanceof FruitTransaction)) {
            return false;
        }

        FruitTransaction other = (FruitTransaction) o;
        return other.getQuantity() == getQuantity()
            && other.operation == operation
            && Objects.equals(other.fruit.getName(), fruit.getName());
    }

    @Override
    public String toString() {
        return fruit.getName() + " " + getQuantity() + " " + getOperation().name();
    }
}
