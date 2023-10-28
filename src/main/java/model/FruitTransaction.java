package model;

import java.util.Objects;

public class FruitTransaction {
    private static final String CAN_NOT_FIND_OPERATION = "Can not find suitable Operation ";
    private final Operation operation;
    private String fruit;
    private final Integer quantity;

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

    public void setFruit(String fruit) {
        this.fruit = fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FruitTransaction that)) {
            return false;
        }
        return operation == that.operation
                && Objects.equals(fruit, that.fruit)
                && Objects.equals(quantity, that.quantity);
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

        public static Operation findRightOperation(String operation) {
            for (Operation op : Operation.values()) {
                if (op.operation.equals(operation)) {
                    return op;
                }
            }
            throw new RuntimeException(CAN_NOT_FIND_OPERATION + operation);
        }
    }

}
