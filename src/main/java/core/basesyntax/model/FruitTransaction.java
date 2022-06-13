package core.basesyntax.model;

public class FruitTransaction {
    private Operation operation;
    private String fruit;
    private int quantity;

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
    public String toString() {
        return "Operation " + operation
                + "; Fruit is " + fruit
                + "; Quantity = " + quantity;
    }

    @Override
    public boolean equals(Object fruitTransaction) {
        if (fruitTransaction == this) {
            return true;
        }
        if (!(fruitTransaction instanceof FruitTransaction)) {
            return false;
        }
        if (fruitTransaction.getClass().equals(FruitTransaction.class)) {
            FruitTransaction other = (FruitTransaction) fruitTransaction;
            boolean operationEquals = (this.operation == null && other.operation == null)
                    || (this.operation != null && this.operation.equals(other.operation));
            boolean fruitEquals = (this.fruit == null && other.fruit == null)
                    || (this.fruit != null && this.fruit.equals(other.fruit));
            boolean quantityEquals = (this.quantity == 0 && other.quantity == 0)
                    || (this.quantity != 0 && this.quantity == other.quantity);
            return operationEquals && fruitEquals && quantityEquals;
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 17;
        if (operation != null) {
            result = prime * result + operation.hashCode();
        }
        if (fruit != null) {
            result = prime * result + fruit.hashCode();
        }
        if (quantity != 0) {
            result = prime * result + quantity;
        }
        return result;
    }

    public enum Operation {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private final String operationType;

        Operation(String operationType) {
            this.operationType = operationType;
        }

        private String getOperationType() {
            return operationType;
        }

        public static Operation getOperationType(String type) {
            for (int i = 0; i < Operation.values().length; i++) {
                if (Operation.values()[i].getOperationType().equals(type)) {
                    return Operation.values()[i];
                }
            }
            throw new RuntimeException("This operation does not exist");
        }
    }
}
