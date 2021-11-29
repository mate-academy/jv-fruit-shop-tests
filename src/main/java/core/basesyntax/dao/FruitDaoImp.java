package core.basesyntax.dao;

import core.basesyntax.models.Fruit;
import core.basesyntax.models.FruitRecord;
import core.basesyntax.storage.Storage;
import java.util.Objects;
import java.util.Set;

public class FruitDaoImp implements FruitDao {
    private Storage storage;

    public FruitDaoImp(Storage storage) {
        this.storage = Objects.requireNonNull(storage);
    }

    @Override
    public void put(FruitRecord fruitRecord) {
        Objects.requireNonNull(fruitRecord);
        Set<Fruit> fruitsInStorage = storage.getFruitsInStorage();
        for (Fruit fruitInStorage : fruitsInStorage) {
            if (!fruitInStorage.getName().equals(fruitRecord.getNameOfFruit())) {
                continue;
            }
            fruitInStorage.setAmount(fruitInStorage.getAmount()
                    + fruitRecord.getAmount());
            break;
        }
    }

    @Override
    public void save(FruitRecord fruitRecord) {
        Objects.requireNonNull(fruitRecord);
        Set<Fruit> fruitsInStorage = storage.getFruitsInStorage();
        fruitsInStorage.add(new Fruit(fruitRecord.getNameOfFruit(), fruitRecord.getAmount()));
    }

    @Override
    public Set<Fruit> get() {
        return storage.getFruitsInStorage();
    }
}
