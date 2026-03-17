package core.basesyntax.db;

import java.util.Map;

public interface Storage {
    Map<String, Integer> getAll();

    Integer getByKey(String key);

    void update(String key, Integer value);
}
