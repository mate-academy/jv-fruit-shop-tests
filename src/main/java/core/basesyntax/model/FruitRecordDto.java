package core.basesyntax.model;

import java.util.Objects;

public class FruitRecordDto {
    private String typeOperation;
    private String fruit;
    private String quantity;

    public String getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
    }

    public String getFruit() {
        return fruit;
    }

    public void setFruit(String fruit) {
        this.fruit = fruit;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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
        return Objects.equals(typeOperation, that.typeOperation)
                && Objects.equals(fruit, that.fruit) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeOperation, fruit, quantity);
    }
}
