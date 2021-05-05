package core.basesyntax.model.dto;

import core.basesyntax.model.Fruit;
import core.basesyntax.operations.Operations;
import java.util.Objects;

public class FruitDataDto {
    private Operations operationType;
    private Fruit fruit;
    private Integer fruitQuantity;

    public FruitDataDto(Operations operationType, Fruit fruit, Integer fruitQuantity) {
        this.operationType = operationType;
        this.fruit = fruit;
        this.fruitQuantity = fruitQuantity;
    }

    public Operations getOperationType() {
        return operationType;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public Integer getFruitQuantity() {
        return fruitQuantity;
    }

    @Override
    public boolean equals(Object fruitDataDto) {
        if (fruitDataDto == this) {
            return true;
        }
        if (fruitDataDto == null || !fruitDataDto.getClass().equals(FruitDataDto.class)) {
            return false;
        }
        FruitDataDto current = (FruitDataDto) fruitDataDto;
        return Objects.equals(this.operationType, current.operationType)
                && Objects.equals(this.fruitQuantity, current.fruitQuantity)
                && Objects.equals(this.fruit, current.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationType, fruitQuantity, fruit);
    }
}
