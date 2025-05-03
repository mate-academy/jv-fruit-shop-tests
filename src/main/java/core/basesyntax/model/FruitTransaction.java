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
        if (quantity < 0) {
            throw new RuntimeException(
                    "Quantity can't be less than 0!"
            );
        }
        this.quantity = quantity;
    }

    public enum Operation {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private String code;

        Operation(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public static Operation getOperationByCode(String operationCode) {
            for (Operation operation : Operation.values()) {
                if (operation.code.equals(operationCode)) {
                    return operation;
                }
            }
            throw new RuntimeException("Wrong operation!!!");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FruitTransaction fruitTransaction = (FruitTransaction) o;
        return operation == fruitTransaction.operation
                && quantity == fruitTransaction.quantity
                && Objects.equals(fruit, fruitTransaction.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, quantity);
    }
}
