package core.basesyntax.model;

import java.util.Objects;

public class Transaction {
    private Fruit fruit;
    private Integer amount;
    private String operation;

    public Transaction(String operation, Fruit fruit, Integer amount) {
        this.fruit = fruit;
        this.amount = amount;
        this.operation = operation;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (object.getClass() == Transaction.class) {
            Transaction transaction = (Transaction) object;
            return Objects.equals(transaction.getFruit(), fruit)
                    && Objects.equals(transaction.getOperation(), operation)
                    && Objects.equals(transaction.getAmount(), amount);
        }
        return false;
    }
}
