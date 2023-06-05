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

    public String getFruit() {
        return fruit;
    }

    public Operation getOperation() {
        return operation;
    }

    public int getQuantity() {
        return quantity;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FruitTransaction other = (FruitTransaction) obj;
        return operation == other.operation
                && Objects.equals(fruit, other.fruit)
                && quantity == other.quantity;
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

        public String getCode() {
            return code;
        }

        public static Operation getOperationFromCode(String code) {
            return Arrays.stream(Operation.values())
                    .filter(o -> o.getCode().equals(code))
                    .findFirst()
                    .orElseThrow();
        }
    }
}
