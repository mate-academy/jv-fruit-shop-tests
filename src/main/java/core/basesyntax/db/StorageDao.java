package core.basesyntax.db;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public interface StorageDao {

    void add(FruitTransaction fruit);

    int actualQuantity(String fruitName);

    void updateQuantity(String fruitName, int quantity);

    Map<String, Integer> getData();

    boolean checkFruit(String fruitName);
}
