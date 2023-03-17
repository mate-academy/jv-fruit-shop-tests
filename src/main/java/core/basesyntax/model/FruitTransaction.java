package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private String operationCharacter;
    private String fruit;
    private int quantity;

    public FruitTransaction(String operationCharacter, String fruit, int quantity) {
        this.operationCharacter = operationCharacter;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public String getOperationCharacter() {
        return operationCharacter;
    }

    public void setOperationCharacter(String operationCharacter) {
        this.operationCharacter = operationCharacter;
    }

    public String getFruit() {
        return fruit;
    }

    public void setFruit(String fruit) {
        this.fruit = fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
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
        FruitTransaction that = (FruitTransaction) o;
        return quantity == that.quantity
                && Objects.equals(operationCharacter, that.operationCharacter)
                && Objects.equals(fruit, that.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationCharacter, fruit, quantity);
    }
}
