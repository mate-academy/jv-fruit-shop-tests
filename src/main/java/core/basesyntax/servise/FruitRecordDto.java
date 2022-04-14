package core.basesyntax.servise;

import core.basesyntax.model.Fruit;
import java.util.Objects;

public class FruitRecordDto {
    private Operation type;
    private Fruit fruit;
    private int amountOfFruit;

    public FruitRecordDto(Operation type, Fruit fruit, int amountOfFruit) {
        this.type = type;
        this.fruit = fruit;
        this.amountOfFruit = amountOfFruit;
    }

    public Operation getType() {
        return type;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public int getAmountOfFruit() {
        return amountOfFruit;
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
        return amountOfFruit == that.amountOfFruit && type
                == that.type && Objects.equals(fruit, that.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, fruit, amountOfFruit);
    }

    @Override
    public String toString() {
        return "FruitRecordDto{"
                + "type=" + type
                + ", fruit=" + fruit
                + ", amountOfFruit=" + amountOfFruit
                + '}';
    }
}
