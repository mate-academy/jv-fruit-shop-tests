package core.basesyntax.model;

import java.util.Objects;

public class FruitRecordDto {
    private final String operation;
    private final Fruit fruit;
    private final int amount;

    public FruitRecordDto(String operation, Fruit fruit, int amount) {
        this.operation = operation;
        this.fruit = fruit;
        this.amount = amount;
    }

    public String getOperation() {
        return operation;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object fruit) {
        if (this == fruit) {
            return true;
        }
        if (fruit == null || getClass() != fruit.getClass()) {
            return false;
        }
        FruitRecordDto fruitRecordDto = (FruitRecordDto) fruit;
        return amount == fruitRecordDto.amount
                && Objects.equals(operation, fruitRecordDto.operation)
                && Objects.equals(this.fruit, fruitRecordDto.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, amount);
    }
}
