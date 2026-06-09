package core.basesyntax.model;

public class FruitTransaction {
    private String fruit;
    private int quantity;
    private Operation operation;

    public String getFruit() {
        return fruit;
    }

    public void setFruit(String code) {
        for (Fruits fruit: Fruits.values()) {
            if (fruit.getCode().equals(code)) {
                this.fruit = code;
                return;
            }
        }
        throw new RuntimeException("such fruit don't exist");
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

    public void setOperation(String code) {
        for (Operation operation : Operation.values()) {
            if (operation.getCode().equals(code)) {
                this.operation = operation;
                return;
            }
        }
        throw new RuntimeException("operation don't exist for this code");
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

    public enum Fruits {
        APPLE("apple"),
        BANANA("banana");

        private final String code;

        Fruits(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}
