package core.basesyntax.model;

import java.util.NoSuchElementException;
import java.util.Objects;

public class FruitTransaction {
    private static Operation operation;
    private static String fruit;

    private static int quantity;

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public String getFruit() {
        return fruit;
    }

    public void setFruit(String fruit) {
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

        private final String letter;

        Operation(String letter) {
            this.letter = letter;
        }

        public String getLetter() {
            return letter;
        }

        public static Operation findOperationByLetter(String letter) {
            if (letter == null) {
                throw new RuntimeException("Input can't be null");
            } else if (!Character.isLetter(letter.charAt(0))) {
                throw new RuntimeException("Input can't be character or number");
            }
            for (Operation operation : Operation.values()) {
                if (operation.getLetter().equals(letter)) {
                    return operation;
                }
            }
            throw new NoSuchElementException("Can't find such Operation" + letter);
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
        return quantity == that.quantity && operation == that.operation
                && Objects.equals(fruit, that.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, quantity);
    }
}
