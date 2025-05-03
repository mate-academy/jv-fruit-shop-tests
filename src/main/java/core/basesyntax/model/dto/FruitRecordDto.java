package core.basesyntax.model.dto;

import java.util.Objects;

public class FruitRecordDto {
    private String operation;
    private String fruitName;
    private Integer quantity;

    public FruitRecordDto(String operation, String fruitName, Integer quantity) {
        this.operation = operation;
        this.fruitName = fruitName;
        this.quantity = quantity;
    }

    public String getOperation() {
        return operation;
    }

    public String getFruitName() {
        return fruitName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FruitRecordDto that = (FruitRecordDto) o;
        return Objects.equals(operation, that.operation)
                && Objects.equals(fruitName, that.fruitName)
                && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruitName, quantity);
    }
}
