package core.basesyntax.data;

public class FruitTransaction {
    private static final String EXCEPTION_OPERATION_MESSAGE = "No such operation";
    private int quantity;
    private Operation operation;
    private String fruit;

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

        private String code;

        Operation(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public static Operation getOperationByCode(String code) {
            for (Operation value : Operation.values()) {
                if (value.getCode().equals(code)) {
                    return value;
                }
            }
            throw new RuntimeException(EXCEPTION_OPERATION_MESSAGE);
        }
    }
}
