package core.basesyntax.model.service;

import java.util.Map;
import java.util.Set;

public interface Storage {
    void add(String fruit, Integer quantity);

    void remove(String fruit, Integer quantity);

    public Set<Map.Entry<String, Integer>> getEntrySet();

    public void clearStorage();
}
