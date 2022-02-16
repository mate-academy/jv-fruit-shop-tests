package core.basesyntax.service;

import java.util.Map;

public interface StorageService {
    void addToStorage(String fruit,Integer value);

    Integer getFromStorage(String fruit);

    Map<String,Integer> getStorage();
}
