package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {

    private Operation operation;
    private String fruit;
    private int quantity;

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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FruitTransaction that = (FruitTransaction) o;
        return getQuantity() == that.getQuantity() && getOperation()
                == that.getOperation() && Objects.equals(getFruit(), that.getFruit());
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

        private final String abbrOperation;

        Operation(String abbrOperation) {
            this.abbrOperation = abbrOperation;
        }

        public String getAbbrOperation() {
            return abbrOperation;
        }

        public static Operation getOperation(String abbrOperation) {
            for (Operation operation : Operation.values()) {
                if (operation.getAbbrOperation().equals(abbrOperation)) {
                    return operation;
                }
            }
            throw new RuntimeException("Operation does not correct");
        }
    }
}
