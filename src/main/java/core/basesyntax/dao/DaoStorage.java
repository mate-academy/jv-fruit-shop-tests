package core.basesyntax.dao;

import java.util.Map;
import java.util.Set;

public interface DaoStorage {
    void setNewValue(String fruit, Integer quantity);

    void concatenateValue(String fruit, Integer quantity);

    int getValue(String fruit);

    Set<Map.Entry<String, Integer>> getStatistic();

    void clear();
}
