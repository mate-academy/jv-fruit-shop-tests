package model;

import java.util.Objects;

public class FruitRecord {
    private char typeOfOperation;
    private String nameOfFruit;
    private int amount;

    public FruitRecord(char typeOfOperation, String nameOfFruit, int amount) {
        this.typeOfOperation = typeOfOperation;
        this.nameOfFruit = nameOfFruit;
        this.amount = amount;
    }

    public char getTypeOfOperation() {
        return typeOfOperation;
    }

    public String getNameOfFruit() {
        return nameOfFruit;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FruitRecord that = (FruitRecord) o;
        return typeOfOperation == that.typeOfOperation
                && amount == that.amount
                && Objects.equals(nameOfFruit, that.nameOfFruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeOfOperation, nameOfFruit, amount);
    }
}
