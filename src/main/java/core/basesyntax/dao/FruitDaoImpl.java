package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.FruitStrategy;
import core.basesyntax.service.StringTransformator;
import core.basesyntax.service.impl.FruitStrategyImpl;
import core.basesyntax.service.impl.StringTransformatorImpl;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FruitDaoImpl implements FruitDao {
    private static final int OPERATION_ABBREVIATION = 0;

    @Override
    public void putDataIntoStorage(List<String> data) {
        FruitStrategy strategy = new FruitStrategyImpl();
        StringTransformator transformator = new StringTransformatorImpl();
        List<String[]> listOfArrays = transformator.transformStrings(data);
        for (String[] dataArray : listOfArrays) {
            strategy.makeOperation(dataArray[OPERATION_ABBREVIATION]).operation(dataArray);
        }
    }

    @Override
    public Set<Map.Entry<Fruit, Integer>> getDataFromStorage() {
        return Storage.FRUIT_STORAGE.entrySet();
    }
}
