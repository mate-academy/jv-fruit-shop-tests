package core.basesyntax.fruit.dto;

import java.util.Objects;

public class FruitDto {
    private final String fruitType;
    private final int amount;

    public FruitDto(String fruitType, int amount) {
        this.fruitType = fruitType;
        this.amount = amount;
    }

    public String getFruitType() {
        return fruitType;
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
        FruitDto fruitDto = (FruitDto) o;
        return amount == fruitDto.amount && Objects.equals(fruitType, fruitDto.fruitType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruitType, amount);
    }
}
