package core.basesyntax.testclasses;

import core.basesyntax.dao.DaoStorage;
import java.util.Map;
import java.util.Set;

public class DaoStorageForTest implements DaoStorage {
    @Override
    public void setNewValue(String fruit, Integer quantity) {
        StorageForTest.getTestStorage().put(fruit, quantity);
    }

    @Override
    public void concatenateValue(String fruit, Integer quantity) {
        StorageForTest.getTestStorage().merge(fruit, quantity, Integer::sum);
    }

    @Override
    public int getValue(String fruit) {
        return StorageForTest.getTestStorage().get(fruit);
    }

    @Override
    public Set<Map.Entry<String, Integer>> getStatistic() {
        return StorageForTest.getTestStorage().entrySet();
    }

    @Override
    public void clear() {
        StorageForTest.getTestStorage().clear();
    }
}
