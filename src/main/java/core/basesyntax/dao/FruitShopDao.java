package core.basesyntax.dao;

public interface FruitShopDao {
    int getQuantity(String fruit);

    void put(String fruit, Integer quantity);
}
