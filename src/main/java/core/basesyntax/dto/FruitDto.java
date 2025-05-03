package core.basesyntax.dto;

import core.basesyntax.operations.Operation;
import java.util.Objects;

public class FruitDto {
    private Operation operation;
    private String fruitName;
    private Integer countFruit;

    public FruitDto(Operation operation, String fruitName, Integer countFruit) {
        this.operation = operation;
        this.fruitName = fruitName;
        this.countFruit = countFruit;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getFruitName() {
        return fruitName;
    }

    public Integer getCountFruit() {
        return countFruit;
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
        return operation == fruitDto.operation
                && Objects.equals(fruitName, fruitDto.fruitName)
                && Objects.equals(countFruit, fruitDto.countFruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruitName, countFruit);
    }
}
