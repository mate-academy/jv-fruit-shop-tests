package core.basesyntax.db;

public interface ShopDao {
    void addFruitToBalance(String fruit, Integer quantity);

    void purchaseFruit(String fruit, Integer quantity);

    void returnOrSupplyFruit(String fruit, Integer quantity);
}
