package core.basesyntax.model.dto;

import java.util.Objects;

public class FruitDto {
    private final String typeOfOperation;
    private final String fruitName;
    private final int fruitQuantity;

    public FruitDto(String operationType, String fruitName, int fruitQuantity) {
        this.typeOfOperation = operationType;
        this.fruitName = fruitName;
        this.fruitQuantity = fruitQuantity;
    }

    public String getTypeOfOperation() {
        return typeOfOperation;
    }

    public String getFruitName() {
        return fruitName;
    }

    public int getFruitQuantity() {
        return fruitQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FruitDto that = (FruitDto) o;
        return fruitQuantity == that.fruitQuantity
                && typeOfOperation.equals(that.typeOfOperation)
                && fruitName.equals(that.fruitName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeOfOperation, fruitName, fruitQuantity);
    }
}
