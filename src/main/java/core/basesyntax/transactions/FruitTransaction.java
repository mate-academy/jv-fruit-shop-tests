package core.basesyntax.transactions;

public class FruitTransaction {
    private Operation operation;
    private String fruit;
    private int quantity;

    public Operation getOperation() {
        return operation;
    }

    public FruitTransaction setOperation(Operation operation) {
        if (operation == null) {
            throw new IllegalArgumentException("Operation can't be null!");
        }

        this.operation = operation;
        return this;
    }

    public String getFruit() {
        return fruit;
    }

    public FruitTransaction setFruit(String fruit) {
        if (fruit == null) {
            throw new IllegalArgumentException("Fruit can't be null!");
        }

        if (fruit.isEmpty()) {
            throw new IllegalArgumentException("Fruit can't be empty!");
        }

        if (fruit.contains(" ")) {
            throw new IllegalArgumentException("Fruit contains spaces!");
        }

        this.fruit = fruit;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public FruitTransaction setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity can't be negative");
        }

        this.quantity = quantity;
        return this;
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
    }
}
