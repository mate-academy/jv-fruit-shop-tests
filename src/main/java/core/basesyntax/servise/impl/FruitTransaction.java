package core.basesyntax.servise.impl;

public class FruitTransaction {
    private static final String PATTERN = "[a-z]+";
    private final Operation operation;
    private final String fruit;
    private final int quantity;

    public FruitTransaction(String code, String fruit, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Number is negative:" + quantity);
        }

        if (fruit == null || !fruit.matches(PATTERN)) {
            throw new IllegalArgumentException("Fruit name is incorrect");
        }
        this.operation = Operation.valueOfCode(code);
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

        public static Operation valueOfCode(String code) {
            for (Operation element : values()) {
                if (element.getCode().equals(code)) {
                    return element;
                }
            }
            throw new IllegalArgumentException("Invalid code value of operation: " + code);
        }
    }
}
