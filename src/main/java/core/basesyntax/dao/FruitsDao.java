package core.basesyntax.dao;

import java.util.HashMap;
import java.util.Map;

public interface FruitsDao {

    void addFruit(String fruit, int quantity);

    int getQuantityByFruit(String fruit);

    Boolean contains(String fruit);

    Map<String, Integer> getAll();
}
