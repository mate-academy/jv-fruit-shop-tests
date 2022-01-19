package core.basesyntax.model;

import java.util.Objects;

public class Transaction {
    private final TransactionType type;
    private final String fruitName;
    private final int amount;

    public Transaction(TransactionType type, String fruitName, int amount) {
        this.type = type;
        this.fruitName = fruitName;
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public String getFruitName() {
        return fruitName;
    }

    public int getAmount() {
        return amount;
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
        return amount == that.amount && type == that.type
                && Objects.equals(fruitName, that.fruitName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, fruitName, amount);
    }

    @Override
    public String toString() {
        return "Transaction{" + type + "=" + type
                + ", fruitName='" + fruitName + '\''
                + ", amount=" + amount + '}';
    }
}
