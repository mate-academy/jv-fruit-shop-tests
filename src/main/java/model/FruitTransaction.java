package model;

import java.util.Arrays;

public class FruitTransaction {
    private Operation operation;
    private String fruit;
    private int amount;

    private FruitTransaction(Operation operation, String fruit, int amount) {
        this.operation = operation;
        this.fruit = fruit;
        this.amount = amount;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getFruit() {
        return fruit;
    }

    public int getAmount() {
        return amount;
    }

    public static FruitTransaction of(Operation operation, String fruit, int amount) {
        return new FruitTransaction(operation, fruit, amount);
    }

    public static Operation getOperationByLetter(String firstLetter) {
        return Arrays.stream(Operation.values())
                .filter(o -> o.getLetter().equals(firstLetter))
                .findFirst().get();
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
    }
}
