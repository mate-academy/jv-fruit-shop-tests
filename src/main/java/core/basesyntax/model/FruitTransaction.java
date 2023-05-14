package core.basesyntax.model;

public class FruitTransaction {
    private Operation operation;
    private String fruit;
    private int quantity;

    public FruitTransaction(String operation, String fruit, int quantity) {
        this.operation = Operation.fromCode(operation);
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public Operation getOperation() {
        return operation;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getFruit() {
        return fruit;
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
            FruitTransaction current = (FruitTransaction) fruitTransaction;
            return this.operation == current.getOperation()
                    && this.fruit.equals(current.fruit)
                    && this.quantity == current.quantity;
        }
        return false;
    }
}
