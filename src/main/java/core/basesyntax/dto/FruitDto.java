package core.basesyntax.dto;

import java.util.Objects;

public class FruitDto {
    private final String name;
    private final int quantity;
    private final String operation;

    public FruitDto(String operation, String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
        this.operation = operation;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getOperation() {
        return operation;
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
        return quantity == fruitDto.quantity && Objects.equals(name,
                fruitDto.name) && Objects.equals(operation, fruitDto.operation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, quantity, operation);
    }
}
