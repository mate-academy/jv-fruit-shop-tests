package core.basesyntax.dao;

import core.basesyntax.dao.validator.ValidatorImpl;
import core.basesyntax.model.FruitModel;
import core.basesyntax.storage.FruitStorage;

public class StorageDaoImpl extends ValidatorImpl implements StorageDao {
    @Override
    public int getAmount(String key) {
        validate(key, 0);
        exceptionIfKeyDoesNotExist(key);
        return FruitStorage.fruitStorage.get(key);
    }

    @Override
    public boolean putFruitModel(FruitModel fruitModel) {
        if (fruitModel == null) {
            throw new RuntimeException("There is no fruitModel");
        }
        validate(fruitModel.getName(), fruitModel.getAmount());
        FruitStorage.fruitStorage.put(fruitModel.getName(), fruitModel.getAmount());
        return true;
    }

    @Override
    public boolean replaceWithNewAmount(String name, int amount) {
        validate(name, amount);
        exceptionIfKeyDoesNotExist(name);
        FruitStorage.fruitStorage.replace(name, amount);
        return true;
    }

    @Override
    public boolean containsKey(String key) {
        validate(key, 0);
        return FruitStorage.fruitStorage.containsKey(key);
    }

    private void exceptionIfKeyDoesNotExist(String key) {
        if (!containsKey(key)) {
            throw new RuntimeException("There is no such fruit " + key);
        }
    }
}
