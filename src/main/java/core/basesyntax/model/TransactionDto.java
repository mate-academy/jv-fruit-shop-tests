package core.basesyntax.model;

import java.util.Objects;

public class TransactionDto {
    private final String type;
    private final Fruit fruit;
    private final int amount;

    public TransactionDto(String type, Fruit fruit, int amount) {
        this.type = type;
        this.fruit = fruit;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public Fruit getFruit() {
        return fruit;
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
        TransactionDto that = (TransactionDto) o;
        return amount == that.amount && Objects.equals(type, that.type)
                && Objects.equals(fruit, that.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, fruit, amount);
    }
}
