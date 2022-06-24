package core.basesyntax.model;

public class FruitTransaction {
    private Operation operation;
    private String fruit;
    private int quantity;

    public FruitTransaction(String letter, String fruit, int quantity) {
        operation = Operation.getOperationByLetter(letter);
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        if (operation == null) {
            throw new RuntimeException("You should indicate some operation");
        }
        this.operation = operation;
    }

    public String getFruit() {
        return fruit;
    }

    public void setFruit(String fruit) {
        if (fruit == null) {
            throw new RuntimeException("You should indicate some fruit");
        }
        this.fruit = fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 1) {
            throw new RuntimeException("You should put more than one fruit");
        }
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "FruitTransaction{"
                + "operation=" + operation
                + ", fruit='" + fruit + '\''
                + ", quantity=" + quantity
                + '}';
    }

    public enum Operation {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private String operation;

        Operation(String operation) {
            this.operation = operation;
        }

        private static Operation getOperationByLetter(String letter) {
            for (Operation value : Operation.values()) {
                if (value.operation.equalsIgnoreCase(letter)) {
                    return value;
                }
            }
            return null;
        }

        public String getOperation() {
            return operation;
        }
    }
}
