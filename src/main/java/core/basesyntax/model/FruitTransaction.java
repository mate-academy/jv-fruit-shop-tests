package core.basesyntax.model;

import core.basesyntax.exceptions.InvalidDataException;
import java.util.Objects;

public class FruitTransaction {
    private final Operation operation;
    private final String fruit;
    private final int quantity;

    public FruitTransaction(Operation operation, String fruit, int quantity) {
        if (!fruit.equals("banana") && !fruit.equals("apple")) {
            throw new InvalidDataException("Unfortunately, we don't have "
                    + fruit + " in our store");
        }
        if (quantity < 0) {
            throw new InvalidDataException("Incorrect data, the quantity cannot be negative.");
        }
        this.operation = operation;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FruitTransaction)) {
            return false;
        }
        FruitTransaction that = (FruitTransaction) o;
        return quantity == that.quantity && operation == that.operation && fruit.equals(that.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, quantity);
    }

    public Operation getOperation() {
        return operation;
    }

    public String getFruit() {
        return fruit;
    }

    public int getQuantity() {
        return quantity;
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

        public static Operation valueOfCode(String code) {
            for (int i = 0; i < values().length; i++) {
                if (values()[i].code.equals(code)) {
                    return values()[i];
                }
            }
            throw new InvalidDataException("Maybe you specified a non-existent operation, "
                    + "you entered " + code);
        }
    }
}
