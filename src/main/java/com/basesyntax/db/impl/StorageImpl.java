package com.basesyntax.db.impl;

import com.basesyntax.db.Storage;
import com.basesyntax.model.Fruit;
import com.basesyntax.services.impl.ConverterMapToListImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StorageImpl implements Storage {
    private static final String COMMA = ",";
    private static final Map<Fruit, Integer> storage = new HashMap<>();

    @Override
    public Map<Fruit, Integer> getStorage() {
        return storage;
    }

    @Override
    public List<String> getStorageAsList() {
        return new ConverterMapToListImpl().convert(storage);
    }
}
