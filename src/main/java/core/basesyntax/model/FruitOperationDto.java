package core.basesyntax.model;

import java.util.Objects;

public class FruitOperationDto {
    private OperationType type;
    private Fruit fruit;
    private int quantity;

    public FruitOperationDto(OperationType type, Fruit fruit, int quantity) {
        this.type = type;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FruitOperationDto that = (FruitOperationDto) o;
        return quantity == that.quantity
                && type == that.type
                && Objects.equals(fruit, that.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, fruit, quantity);
    }
}
