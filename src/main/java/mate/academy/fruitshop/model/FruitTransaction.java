package mate.academy.fruitshop.model;

public class FruitTransaction {
    private Operation operation;
    private String fruit;
    private int quantity;

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        if (operation == null) {
            throw new RuntimeException("Operation cannot be null");
        }
        this.operation = operation;
    }

    public String getFruit() {
        return fruit;
    }

    public void setFruit(String fruit) {
        if (fruit == null || fruit.isBlank()) {
            throw new RuntimeException("Fruit cannot be null or empty");
        }
        this.fruit = fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new RuntimeException("Quantity cannot be negative");
        }
        this.quantity = quantity;
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
                throw new RuntimeException("Operation code cannot be null");
            }

            for (Operation operation : values()) {
                if (operation.code.equalsIgnoreCase(code)) {
                    return operation;
                }
            }
            throw new RuntimeException("Unknown operation: " + code);
        }
    }
}
