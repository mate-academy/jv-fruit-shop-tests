package core.basesyntax.model;

public class Transaction {
    private Operation operation;
    private String fruit;
    private Integer amount;

    public Transaction(Operation operation,
                       String fruit, int amount) {
        this.operation = operation;
        this.fruit = fruit;
        this.amount = amount;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getFruit() {
        return fruit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Transaction that = (Transaction) o;

        if (operation != that.operation) {
            return false;
        }
        if (fruit != null ? !fruit.equals(that.fruit) : that.fruit != null) {
            return false;
        }
        return amount != null ? amount.equals(that.amount) : that.amount == null;
    }

    public int getAmount() {
        return amount;
    }
}
