package model;

public class FruitTransaction extends Fruit {

    private static final String ERROR = "Unsupported operation code: ";
    private Operation operation;

    public FruitTransaction() {

    }

    public FruitTransaction(Operation operation, Fruit fruit) {
        super(fruit.getName(), fruit.getQuantity());
        this.operation = operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Operation getOperationType() {
        return operation;
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
            for (Operation op : Operation.values()) {
                if (op.getCode().equals(code)) {
                    return op;
                }
            }
            throw new IllegalArgumentException(ERROR + code);
        }
    }
}
