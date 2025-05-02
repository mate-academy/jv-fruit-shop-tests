package core.basesyntax.db;

import java.util.Set;

public interface Storage {
    void setValue(String key, Integer value);

    Integer getValue(String key);

    Set<String> getKeys();

    void clear();
}
