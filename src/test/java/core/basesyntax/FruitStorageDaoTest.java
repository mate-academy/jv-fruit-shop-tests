package core.basesyntax;

import static core.basesyntax.db.FruitStorage.storage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FruitStorageDaoTest {
    private static final String TEST_FRUIT_NAME = "banana";
    private static final int TEST_QUANTITY = 20;
    private static final int TEST_QUANTITY_2 = 40;
    private final Map<Operation, OperationHandler> operationMap = new HashMap<>();
    private final OperationStrategy operationStrategy = new OperationStrategyImpl(operationMap);
    private final FruitStorageDao fruitStorageDao = new FruitStorageDaoImpl(operationStrategy);

    @Before
    public void setUp() {
        operationMap.put(Operation.BALANCE, new BalanceOperationHandler());
        operationMap.put(Operation.SUPPLY, new SupplyOperationHandler());
        operationMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        operationMap.put(Operation.RETURN, new ReturnOperationHandler());
    }

    @After
    public void afterEach() {
        storage.clear();
    }

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
    public void addToStorage_alreadyExistKey_Ok() {
        List<FruitTransaction> activities = new ArrayList<>();
        activities.add(new FruitTransaction(Operation.BALANCE,
                TEST_FRUIT_NAME, TEST_QUANTITY));
        activities.add(new FruitTransaction(Operation.BALANCE,
                TEST_FRUIT_NAME, TEST_QUANTITY_2));
        fruitStorageDao.addToStorage(activities);
        Integer expectedValue = operationStrategy
                .get(Operation.BALANCE)
                .apply(TEST_QUANTITY, TEST_QUANTITY_2);
        Map<String, Integer> expected = Map.of(TEST_FRUIT_NAME, expectedValue);
        assertEquals(expected, storage);
    }

    @Test
    public void addToStorage_NullData_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fruitStorageDao.addToStorage(null);
        });
    }
}
