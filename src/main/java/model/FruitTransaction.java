package model;

public class FruitTransaction extends Fruit {

    private static final String ERROR = "Unsupported operation code: ";
    private OperationType operation;

    public FruitTransaction() {

    }

    public FruitTransaction(OperationType operation, Fruit fruit) {
        super(fruit.getName(), fruit.getQuantity());
        this.operation = operation;
    }

    public void setOperation(OperationType operation) {
        this.operation = operation;
    }

    public OperationType getOperationType() {
        return operation;
    }

    public enum OperationType {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private final String code;

        OperationType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public static OperationType fromCode(String code) {
            for (OperationType operationType : OperationType.values()) {
                if (operationType.getCode().equals(code)) {
                    return operationType;
                }
            }
            throw new IllegalArgumentException(ERROR + code);
        }
    }
}
