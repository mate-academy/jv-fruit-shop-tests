package core.basesyntax.model;

import java.util.Objects;

public class FruitTransferDto {
    private String operationType;
    private Fruit fruit;
    private int quantity;

    public FruitTransferDto(String operationType, Fruit fruit, int quantity) {
        this.operationType = operationType;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public String getOperationType() {
        return operationType;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationType, fruit, quantity);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass().equals(FruitTransferDto.class)) {
            FruitTransferDto current = (FruitTransferDto) obj;
            return Objects.equals(operationType, current.operationType)
                    && Objects.equals(fruit, current.fruit)
                    && quantity == this.quantity;
        }
        return false;
    }
}
