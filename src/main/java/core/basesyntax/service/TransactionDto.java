package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import java.util.Objects;

public class TransactionDto {
    private OperationType type;
    private Fruit fruit;
    private Integer quantity;

    public TransactionDto(OperationType type, Fruit fruit, Integer quantity) {
        this.type = type;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public OperationType getType() {
        return type;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o.getClass().equals(TransactionDto.class))) {
            return false;
        }
        TransactionDto that = (TransactionDto) o;
        return type == that.type && Objects.equals(fruit, that.fruit)
                && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, fruit, quantity);
    }
}
