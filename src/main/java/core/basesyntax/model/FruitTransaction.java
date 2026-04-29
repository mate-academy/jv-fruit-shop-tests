package core.basesyntax.model;

public class FruitTransaction {
    private String name;
    private int quantity;
    private Operation operation;

    public FruitTransaction(Operation operation, String name, int quantity) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Fruit name cannot be null or blank");
        }
        this.name = name;
        if (quantity < 0) {
            throw new IllegalArgumentException("quantity can't be negative");
        }
        this.quantity = quantity;
        this.operation = operation;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Operation getOperation() {
        return operation;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = hash + name.hashCode() * 11
                + quantity
                + operation.hashCode() * 13;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FruitTransaction fruitTransaction = (FruitTransaction) obj;
        return quantity == fruitTransaction.quantity
                && name.equals(fruitTransaction.name)
                && operation.equals(fruitTransaction.operation);
    }
}

