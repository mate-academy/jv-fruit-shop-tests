package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private FruitShopOperation fruitShopOperation;
    private String fruit;
    private int quantity;

    public FruitTransaction(FruitShopOperation fruitShopOperation, String fruit, int quantity) {
        this.fruitShopOperation = fruitShopOperation;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public FruitShopOperation getOperation() {
        return fruitShopOperation;
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
                && fruitShopOperation == that.fruitShopOperation
                && fruit.equals(that.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruitShopOperation, fruit, quantity);
    }
}
