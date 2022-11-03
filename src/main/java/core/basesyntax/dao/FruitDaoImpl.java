package core.basesyntax.dao;

import core.basesyntax.db.DataBase;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.ElementDoesNotExist;
import java.util.Map;

public class FruitDaoImpl implements FruitDao {
    public void add(String typeOfFruit, Fruit fruit) {
        if (typeOfFruit == null) {
            throw new ElementDoesNotExist("Can not use Null as key to storage");
        }
        if (fruit == null) {
            throw new ElementDoesNotExist("Can not add Null to storage");
        }
        DataBase.fruitsInShop.put(typeOfFruit, fruit);
    }

    public Fruit get(String keyFruit) {
        if (keyFruit == null) {
            throw new ElementDoesNotExist("Can not get Null from storage");
        }
        return DataBase.fruitsInShop.get(keyFruit);
    }

    public Map<String,Fruit> getAll() {
        return DataBase.fruitsInShop;
    }
}
