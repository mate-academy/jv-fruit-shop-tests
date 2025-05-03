package core.basesyntax.model;

public class FruitTransaction {
    private final Operation operation;
    private final String fruit;
    private final int quantity;

    public FruitTransaction(Operation operation, String fruit, int quantity) {
        if (operation == null) {
            throw new IllegalArgumentException("Operation cannot be null");
        }
        if (fruit == null || fruit.trim().isEmpty()) {
            throw new IllegalArgumentException("Fruit cannot be null or empty");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be a positive number");
        }

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

    public int getQuantity() {
        return quantity;
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

        public static Operation fromCode(String code) {
            if (code == null) {
                throw new IllegalArgumentException("Operation code cannot be null");
            }
            for (Operation operation : Operation.values()) {
                if (operation.getCode().equals(code)) {
                    return operation;
                }
            }
            throw new IllegalArgumentException("Unknown operation: " + code);
        }
    }
}
