package core.basesyntax.dao;

public interface FruitDao {
    void putToDb(String fruit, Integer quantity);

    void retrieve(String fruit, Integer quantity);

    Integer getQuantity(String fruit);
}
