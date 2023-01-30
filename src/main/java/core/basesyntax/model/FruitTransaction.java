package core.basesyntax.model;

public class FruitTransaction {

    private Operation operation;
    private String fruit;
    private int quantity;

    public FruitTransaction(Operation operation, String fruit, int quantity) {
        this.operation = operation;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public FruitTransaction() {
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj.getClass().equals(FruitTransaction.class)) {
            FruitTransaction current = (FruitTransaction) obj;
            return (operation.equals(current.operation))
                    && (fruit.equals(current.fruit))
                    && (quantity == current.quantity);
        }
        return false;
    }

    public enum Operation {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private final String operation;

        Operation(String operation) {
            this.operation = operation;
        }

        public String getOperation() {
            return operation;
        }

        public static Operation getByCode(String code) {
            for (Operation operation : values()) {
                if (operation.getOperation().equals(code)) {
                    return operation;
                }
            }
            throw new RuntimeException("Incorrect code " + code);
        }
    }
}

