package core.basesyntax.database;

import java.util.Objects;

public class FruitDto {
    private String name;
    private int amount;

    public FruitDto(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object fruitDto) {
        String fruitDtoName = ((FruitDto) fruitDto).getName();
        int fruitDtoAmount = ((FruitDto) fruitDto).getAmount();
        return Objects.equals(fruitDtoName, name) && amount == fruitDtoAmount;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + name.hashCode();
        result = 31 * result + amount;
        return result;
    }
}
