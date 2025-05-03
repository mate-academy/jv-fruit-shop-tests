package core.basesyntax.model;

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

    public int getQuantity() {
        return quantity;
    }

    public String getFruit() {
        return fruit;
    }

    public Operation getOperation() {
        return operation;
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
        return Objects.equals(fruit, other.fruit) && operation == other.operation 
                && quantity == other.quantity;
    }

    public enum Operation {
        BALANCE("b"), SUPPLY("s"), PURCHASE("p"), RETURN("r");

        private String code;

        Operation(String operation) {
            this.code = operation;
        }

        public String getOperation() {
            return code;
        }

        public static Operation getByCode(String code) {
            for (Operation o : Operation.values()) {
                if (o.code.equalsIgnoreCase(code)) {
                    return o;
                }
            }
            throw new IllegalArgumentException("Wrong operation code.");
        }
    }
}
