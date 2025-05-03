package core.basesyntax.model;

import java.util.Objects;

public class OperationFruitDto {
    private String operation;
    private String name;
    private int quantity;

    public OperationFruitDto(String operation, String name, int quantity) {
        this.operation = operation;
        this.name = name;
        this.quantity = quantity;
    }

    public String getOperation() {
        return operation;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
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
        OperationFruitDto that = (OperationFruitDto) o;
        return quantity == that.quantity
                && Objects.equals(operation, that.operation)
                && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, name, quantity);
    }
}
