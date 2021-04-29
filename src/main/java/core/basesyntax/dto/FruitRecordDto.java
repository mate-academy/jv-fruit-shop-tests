package core.basesyntax.dto;

import core.basesyntax.model.Fruit;
import java.util.Objects;

public class FruitRecordDto {
    private Fruit fruit;
    private long amount;
    private Type type;

    public FruitRecordDto(Type type, String fruitName, long amount) {
        this.fruit = new Fruit(fruitName, amount);
        this.amount = amount;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FruitRecordDto that = (FruitRecordDto) o;
        return amount == that.amount && Objects.equals(fruit, that.fruit) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruit, amount, type);
    }

    public Fruit getFruit() {
        return fruit;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        p, s, b, r
    }
}

