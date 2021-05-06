package core.basesyntax.dto;

import java.util.Objects;

public class ProductDto {
    private String operationType;
    private String fruitName;
    private Integer quantity;

    public ProductDto(String operationType, String fruitName, Integer quantity) {
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
    public String toString() {
        return "ProductDto{"
                + "operationType='" + operationType + '\''
                + ", fruitName='" + fruitName + '\''
                + ", quantity=" + quantity + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductDto that = (ProductDto) o;
        return Objects.equals(operationType, that.operationType)
                && Objects.equals(fruitName, that.fruitName)
                && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationType, fruitName, quantity);
    }
}
