package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    public static final String BALANCE_MARKER = "b";
    public static final String SUPPLY_MARKER = "s";
    public static final String RETURN_MARKER = "r";
    public static final String PURCHASE_MARKER = "p";
    private Operation operation;
    private String fruit;
    private int quantity;

    public FruitTransaction.Operation getOperationLetter(String letter) {
        for (Operation o : Operation.values()) {
            if (o.operation.equals(letter)) {
                return o;
            }
        }
        throw new RuntimeException("Invalid operation type");
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object fruitTransaction) {
        if (this == fruitTransaction) {
            return true;
        }
        if (!(fruitTransaction instanceof FruitTransaction)) {
            return false;
        }
        FruitTransaction that = (FruitTransaction) fruitTransaction;
        return getQuantity() == that.getQuantity()
                && Objects.equals(getOperation(), that.getOperation())
                && Objects.equals(getFruit(), that.getFruit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOperation(), getFruit(), getQuantity());
    }

    public enum Operation {
      BALANCE("b"),
      SUPPLY("s"),
      PURCHASE("p"),
      RETURN("r");

        private final String operation;

        Operation(String operation) {
            this.operation = operation;
        }

        public String getOperation() {
            return operation;
        }
    }
}
