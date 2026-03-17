package core.basesyntax.db.impl;

import core.basesyntax.db.Storage;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ShopStorage implements Storage {
    private final Map<String, Integer> products = new HashMap<>();

    public Map<String, Integer> getAll() {
        return Collections.unmodifiableMap(products);
    }

    public Integer getByKey(String key) {
        Objects.requireNonNull(key, "key is null");

        if (!products.containsKey(key)) {
            throw new RuntimeException("No such product as " + key);
        }

        return products.get(key);
    }

    public void update(String key, Integer value) {
        Objects.requireNonNull(key, "key is null");
        Objects.requireNonNull(value, "value is null");

        products.put(key, value);
    }
}
