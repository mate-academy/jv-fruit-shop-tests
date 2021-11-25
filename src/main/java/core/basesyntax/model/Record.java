package core.basesyntax.model;

import java.util.Objects;

public class Record {
    private final String transactionAbbr;
    private final String fruitName;
    private final int amount;

    public Record(String transactionAbbr, String fruitName, int amount) {
        this.transactionAbbr = transactionAbbr;
        this.fruitName = fruitName;
        this.amount = amount;
    }

    public String getTransactionAbbr() {
        return transactionAbbr;
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
        if (!(o instanceof Record)) {
            return false;
        }
        Record record = (Record) o;
        return amount == record.amount
                && Objects.equals(transactionAbbr, record.transactionAbbr)
                && Objects.equals(fruitName, record.fruitName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionAbbr, fruitName, amount);
    }
}
