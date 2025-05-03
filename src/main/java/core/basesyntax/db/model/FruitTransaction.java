package core.basesyntax.db.model;

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

    public enum Operation {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private final String operation;

        Operation(String operationStr) {
            this.operation = operationStr;
        }

        public static Operation getOperationStrChar(String operationStr) {
            for (Operation element : Operation.values()) {
                if (element.operation.equals(operationStr)) {
                    return element;
                }
            }
            throw new RuntimeException("Can't convert operation");
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, quantity);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FruitTransaction fruitTransaction = (FruitTransaction) obj;
        return quantity == fruitTransaction.quantity && operation == fruitTransaction.operation
                && Objects.equals(fruit, fruitTransaction.fruit);
    }
}
