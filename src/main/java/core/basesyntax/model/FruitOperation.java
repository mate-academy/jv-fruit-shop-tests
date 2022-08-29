package core.basesyntax.model;

import java.util.NoSuchElementException;

public class FruitOperation {
    private Operation operation;
    private String fruit;
    private int amount;

    public FruitOperation(Operation operation, String fruit, int amount) {
        this.operation = operation;
        this.fruit = fruit;
        this.amount = amount;
    }

    public FruitOperation() {
    }

    @Override
    public int hashCode() {
        int result = 7;
        result = 15 * result + (operation == null ? 0 : operation.hashCode());
        result = 15 * result + (fruit == null ? 0 : fruit.hashCode());
        result = 15 * result + amount;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        FruitOperation current = (FruitOperation) obj;
        return (operation == current.operation || operation != null
                && operation.equals(current.operation))
                && (fruit == current.fruit || fruit != null
                && fruit.equals(current.fruit))
                && (amount == current.amount && amount == current.amount);
    }

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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public enum Operation {
      BALANCE("b"),
      SUPPLY("s"),
      PURCHASE("p"),
      RETURN("r");

        private String letter;

        Operation(String letter) {
            this.letter = letter;
        }

        public String getLetter() {
            return letter;
        }

        public static Operation defineOperationByLetter(String letter) {
            for (Operation operation : Operation.values()) {
                if (operation.getLetter().equals(letter)) {
                    return operation;
                }
            }
            throw new NoSuchElementException("Can't find such Operation" + letter);
        }
    }
}
