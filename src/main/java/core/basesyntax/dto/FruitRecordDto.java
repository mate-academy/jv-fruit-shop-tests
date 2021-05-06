package core.basesyntax.dto;

import java.util.Objects;

public class FruitRecordDto {
    private String operationType;
    private String fruitName;
    private Integer quantity;

    public FruitRecordDto(String operationType, String fruitName, Integer quantity) {
        this.operationType = operationType;
        this.fruitName = fruitName;
        this.quantity = quantity;
    }

    public String getOperationType() {
        return operationType;
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
        FruitRecordDto dto = (FruitRecordDto) o;
        return Objects.equals(operationType, dto.operationType)
                && Objects.equals(fruitName, dto.fruitName)
                && Objects.equals(quantity, dto.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationType, fruitName, quantity);
    }

    @Override
    public String toString() {
        return "FruitRecordDto{"
                + "operationType='" + operationType + '\''
                + ", fruitName='" + fruitName + '\''
                + ", quantity=" + quantity
                + '}';
    }
}
