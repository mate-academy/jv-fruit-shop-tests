package core.basesyntax.dto;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import java.util.Objects;

public class FruitRecordDto {
    private final Operation operationType;
    private final Fruit fruit;
    private final Integer quantity;

    public FruitRecordDto(Operation operationType, Fruit fruit, Integer quantity) {
        this.operationType = operationType;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public Operation getOperationType() {
        return operationType;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object fruitDto) {
        if (this == fruitDto) {
            return true;
        }
        if (fruitDto == null || getClass() != fruitDto.getClass()) {
            return false;
        }
        FruitRecordDto currentFruitRecordDto = (FruitRecordDto) fruitDto;
        return operationType == currentFruitRecordDto.operationType && Objects.equals(fruit,
                currentFruitRecordDto.fruit)
                && Objects.equals(quantity, currentFruitRecordDto.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationType, fruit, quantity);
    }
}
