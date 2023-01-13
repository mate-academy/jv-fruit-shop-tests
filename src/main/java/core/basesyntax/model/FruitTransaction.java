package core.basesyntax.model;

import core.basesyntax.exception.FruitTransactionException;
import java.util.Objects;

public class FruitTransaction {
    private final Operation operation;
    private final String fruit;
    private final int quantity;

    public FruitTransaction(Operation operation, String fruit, int quantity) {
        this.operation = operation;
        this.fruit = fruit;
        this.quantity = quantity;
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

    public static class FruitTransactionBuilder {
        private Operation operation;
        private String fruit;
        private int quantity;

        public FruitTransactionBuilder setOperation(Operation operation) {
            this.operation = operation;
            return this;
        }

        public FruitTransactionBuilder setFruit(String fruit) {
            this.fruit = fruit;
            return this;
        }

        public FruitTransactionBuilder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public FruitTransaction build() {
            return new FruitTransaction(operation, fruit, quantity);
        }
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

        if (quantity != that.quantity) {
            return false;
        }
        if (operation != that.operation) {
            return false;
        }
        return Objects.equals(fruit, that.fruit);
    }

    @Override
    public int hashCode() {
        int result = operation != null ? operation.hashCode() : 0;
        result = 31 * result + (fruit != null ? fruit.hashCode() : 0);
        result = 31 * result + quantity;
        return result;
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

        public static Operation getOperationByLetter(String letter) {
            for (Operation operation : Operation.values()) {
                if (operation.letter.equals(letter)) {
                    return operation;
                }
            }
            throw new FruitTransactionException("Wrong operation letter inserted: " + letter);
        }

        public String getLetter() {
            return letter;
        }
    }
}
