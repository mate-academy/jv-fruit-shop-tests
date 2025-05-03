package core.basesyntax.dao;

import java.util.List;

public interface FruitDao {
    void put(String fruit, int amount);

    int get(String fruit);

    void clear();

    List<FruitDto> getFruits();
}
