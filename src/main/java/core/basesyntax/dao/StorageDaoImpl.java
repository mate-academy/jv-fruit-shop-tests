package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import java.util.Map;

public class StorageDaoImpl implements StorageDao {
    @Override
    public Integer get(String fruit) {
        if (fruit == null || fruit.equals("")) {
            throw new RuntimeException("Input fruit parameter is null null "
                    + "or empty line" + fruit);
        }
        return Storage.getCalculationMap().get(fruit);
    }

    @Override
    public void put(String fruit, Integer amount) {
        if (fruit == null || amount == null) {
            throw new RuntimeException("Null input parameter");
        }
        if (fruit.equals("")) {
            throw new RuntimeException("Incorrect fruit name" + fruit);
        }
        Storage.getCalculationMap().put(fruit, amount);
    }

    @Override
    public Map<String, Integer> getAll() {
        return Storage.getCalculationMap();
    }
}
