package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private Operation operation;
    private String fruit;
    private int quantity;

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

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
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

        public static Operation getByOperationType(String type) {
            for (Operation operation : values()) {
                if (operation.getCode().equals(type)) {
                    return operation;
                }
            }
            throw new IllegalArgumentException("No enum constant found with operations " + type);
        }

        public String getCode() {
            return code;
        }
    }

    @Override
    public boolean equals(Object fruitObj) {
        if (this == fruitObj) {
            return true;
        }
        if (fruitObj == null || getClass() != fruitObj.getClass()) {
            return false;
        }
        FruitTransaction that = (FruitTransaction) fruitObj;
        return quantity == that.quantity
                && operation == that.operation
                && Objects.equals(fruit, that.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, quantity);
    }
}
