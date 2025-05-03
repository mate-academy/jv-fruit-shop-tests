package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private String operationType;
    private Fruit fruit;
    private int quantity;

    public FruitTransaction() {
        this.operationType = operationType;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public FruitTransaction(String operationType, Fruit fruit,
                            Integer quantity) {
        this.operationType = operationType;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public Fruit getTransactionFruitName() {
        return fruit;
    }

    public void setTransactionFruitName(Fruit fruit) {
        this.fruit = fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationType, fruit, quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FruitTransaction fruitTransaction = (FruitTransaction) o;
        return (operationType == fruitTransaction.operationType || operationType != null
                && operationType.equals(fruitTransaction.operationType)
                && (quantity == fruitTransaction.quantity)
                && (fruit == fruitTransaction.fruit || fruit != null
                && fruit.equals(fruitTransaction.fruit)));
    }

    @Override
    public String toString() {
        return operationType + ", " + fruit + ", " + quantity;
    }
}

