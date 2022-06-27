package core.basesyntax.model;

import java.util.Arrays;
import java.util.Objects;

public class FruitTransaction {
    private Operation operation;
    private String fruit;
    private int quantity;

    public FruitTransaction(Operation operation, String fruit, int quantity) {
        this.operation = operation;
        this.fruit = fruit;
        this.quantity = quantity;
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

    public Operation getOperation() {
        return operation;
    }

    public String getFruit() {
        return fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass().equals(o.getClass())) {
            FruitTransaction fruitTransaction = (FruitTransaction) o;
            return quantity == fruitTransaction.quantity
                    && Objects.equals(fruit, fruitTransaction.getFruit())
                    && operation == fruitTransaction.getOperation();
        }
        return false;
    }

    public int hashCode() {
        int h = 17;
        h = h * 31 + fruit.hashCode() + quantity;
        return h * 31 + operation.hashCode();

    }

    public enum Operation {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private String operation;

        Operation(String operation) {
            this.operation = operation;
        }

        public String getOperation() {
            return operation;
        }
    }

    public static Operation getOperationName(String name) {
        return Arrays.stream(Operation.values())
                .filter(operation -> operation.getOperation().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can't find this type of operation: "
                        + name));
    }
}
