package core.basesyntax.dao;

import java.util.Map;

public interface StorageDao {
    void add(String name, Integer quantity);

    Integer get(String name);

    Map<String, Integer> getAll();
}
