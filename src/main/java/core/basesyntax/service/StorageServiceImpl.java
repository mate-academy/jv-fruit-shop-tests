package core.basesyntax.service;

import core.basesyntax.stoage.Storage;
import java.util.Map;

public class StorageServiceImpl implements StorageService {
    @Override
    public void addToStorage(String fruit,Integer value) {
        Storage.storage().put(fruit, value);
    }

    @Override
    public Integer getFromStorage(String fruit) {
        return Storage.storage().get(fruit);
    }

    @Override
    public Map<String, Integer> getStorage() {
        return Storage.storage();
    }
}
