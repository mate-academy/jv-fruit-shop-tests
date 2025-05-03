package core.basesyntax.model;

import java.util.Arrays;
import java.util.Objects;

public class FruitTransaction {
    private Operation operation;
    private Fruit fruit;
    private int quantity;

    public FruitTransaction(Operation operation, Fruit fruit, int quantity) {
        this.operation = operation;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public Operation getOperation() {
        return operation;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
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
        return getQuantity() == that.getQuantity()
                && getOperation()
                == that.getOperation()
                && getFruit().equals(that.getFruit());
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

        public static Operation getByOperation(String operator) {
            return Arrays.stream(values())
              .filter(o -> o.getOperation().equals(operator))
              .findFirst()
              .orElseThrow(() -> new RuntimeException("Can't find operation"));
        }
    }
}
