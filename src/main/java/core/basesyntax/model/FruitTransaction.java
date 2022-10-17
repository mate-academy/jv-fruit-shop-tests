package core.basesyntax.model;

import java.util.Arrays;

public class FruitTransaction {
    private Operation operation;
    private Fruit fruit;
    private int quantity;

    public FruitTransaction(Operation operation, Fruit fruit, int quantity) {
        this.operation = operation;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public FruitTransaction() {
    }

    public static FruitTransaction of(Operation operation, Fruit fruit, int amount) {
        return new FruitTransaction(operation, fruit, amount);
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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public enum Operation {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private final String operationCode;

        Operation(String operationCode) {
            this.operationCode = operationCode;
        }

        public String getOperation() {
            return operationCode;
        }

        public static Operation getByCode(String operator) {
            return Arrays.stream(values())
                    .filter(o -> o.getOperation().equals(operator))
                    .findAny()
                    .get();
        }
    }
}
