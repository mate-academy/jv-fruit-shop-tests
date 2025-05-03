package core.basesyntax.model;

public class FruitTransaction {
    private Operation operation;
    private String fruitName;
    private int quantity;

    public FruitTransaction(String operation, String fruitName, int quantity) {

        this.operation = Operation.getByCode(operation);
        this.fruitName = fruitName;
        this.quantity = quantity;
    }

    public Operation getOperation() {
        return this.operation;
    }

    public void setOperation(String operation) {
        this.operation = Operation.getByCode(operation);
    }

    public String getFruitName() {
        return fruitName;
    }

    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
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

        public static Operation getByCode(String code) {
            for (Operation operation : values()) {
                if (operation.getCode().equals(code)) {
                    return operation;
                }
            }
            throw new RuntimeException("Can't identify operation type for transaction: " + code);
        }
    }

    @Override
    public String toString() {
        return "FruitTransaction{"
                + "operation=" + operation
                + ", fruit='" + fruitName + '\''
                + ", quantity=" + quantity
                + '}';
    }
}
