package core.basesyntax.model;

public class FruitTransaction {
    private Operation operation;
    private String fruit;
    private Integer quantity;

    public FruitTransaction(Operation operation, String fruit, Integer quantity) {
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

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

        public String getNameOperation() {
            return operation;
        }
    }

    @Override
    public int hashCode() {
        int result = 17;
        int primeNumber = 31;
        result = primeNumber * result + (quantity == null ? 0 : quantity.hashCode());
        result = primeNumber * result + (operation == null ? 0 : operation.hashCode());
        result = primeNumber * result + (fruit == null ? 0 : fruit.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object fruitTransaction) {
        if (fruitTransaction == this) {
            return true;
        }
        if (fruitTransaction == null) {
            return false;
        }
        if (fruitTransaction.getClass().equals(FruitTransaction.class)) {
            FruitTransaction currentFruitTransaction =
                    (FruitTransaction) fruitTransaction;
            return (this.operation == currentFruitTransaction.operation
                    || this.operation != null
                    && this.operation.equals(currentFruitTransaction.operation))
                    && (this.fruit == currentFruitTransaction.fruit
                    || this.fruit != null
                    && this.fruit.equals(currentFruitTransaction.fruit))
                    && (this.quantity == currentFruitTransaction.quantity
                    || this.quantity != null
                    && this.quantity.equals(currentFruitTransaction.quantity));
        }
        return false;
    }
}
