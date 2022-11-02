package core.basesyntax;

import static core.basesyntax.db.FruitStorage.storage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class FruitStorageDaoTest {
    private static final String TEST_FRUIT_NAME = "banana";
    private static final int TEST_QUANTITY = 20;
    private final Map<Operation, OperationHandler> operationMap = new HashMap<>();
    private final OperationStrategy operationStrategy = new OperationStrategyImpl(operationMap);
    private final FruitStorageDao fruitStorageDao = new FruitStorageDaoImpl(operationStrategy);

    @Test
    public void addToStorage_correctData_Ok() {
        List<FruitTransaction> activities = new ArrayList<>();
        activities.add(new FruitTransaction(Operation.PURCHASE,
                TEST_FRUIT_NAME, TEST_QUANTITY));
        fruitStorageDao.addToStorage(activities);
        Map<String, Integer> expected = Map.of(TEST_FRUIT_NAME, TEST_QUANTITY);
        assertEquals(expected, storage);
    }

    @Test
    public void addToStorage_NullData_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fruitStorageDao.addToStorage(null);
        });
    }
}
