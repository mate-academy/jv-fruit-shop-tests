package model;

import java.util.Objects;

public class FruitDataDto {
    private String operation;
    private String fruit;
    private int amount;

    public FruitDataDto(String operations, String fruit, int amount) {
        this.operation = operations;
        this.fruit = fruit;
        this.amount = amount;
    }

    public String getOperation() {
        return operation;
    }

    public String getFruit() {
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
        FruitDataDto that = (FruitDataDto) o;
        return amount == that.amount
                && Objects.equals(operation, that.operation)
                && Objects.equals(fruit, that.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, amount);
    }
}
