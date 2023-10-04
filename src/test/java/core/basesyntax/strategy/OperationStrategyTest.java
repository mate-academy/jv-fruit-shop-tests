package core.basesyntax.strategy;

import core.basesyntax.db.FruitStorageDao;
import core.basesyntax.db.FruitStorageDaoImpl;
import core.basesyntax.model.OperationType;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private static OperationStrategy operationStrategy;
    private static FruitStorageDao fruitStorageDao;
    private static Map<String, Integer> db;

    @BeforeAll
    static void beforeAll() {
        Map<OperationType, OperationHandler> strategies = new HashMap<>();
        strategies.put(OperationType.BALANCE, new OperationHandlerBalance());
        strategies.put(OperationType.SUPPLY, new OperationHandlerSupply());
        strategies.put(OperationType.PURCHASE, new OperationHandlerPurchase());
        strategies.put(OperationType.RETURN, new OperationHandlerReturn());
        operationStrategy = new OperationStrategyImpl(strategies);
        fruitStorageDao = new FruitStorageDaoImpl();

        // Get FruitStorageDaoImpl private DB tests
        try {
            Field field = fruitStorageDao.getClass().getDeclaredField("db");
            field.setAccessible(true);
            db = (Map<String, Integer>) field.get(fruitStorageDao);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void get_nullOperationType_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> operationStrategy.get(null));
    }

    @Test
    void get_validData_ok() {
        db.clear();
        String fruitName = "banana";

        // BALANCE
        OperationHandler actualHandler = operationStrategy.get(OperationType.BALANCE);
        Assertions.assertEquals(OperationHandlerBalance.class, actualHandler.getClass());
        actualHandler.makeChanges(fruitStorageDao, fruitName, 100);
        int actualCount = db.get(fruitName);
        Assertions.assertEquals(100, actualCount,
                "Does not match the fruits count: expected "
                        + 100 + ", exist " + actualCount);

        // SUPPLY
        actualHandler = operationStrategy.get(OperationType.SUPPLY);
        Assertions.assertEquals(OperationHandlerSupply.class, actualHandler.getClass());
        actualHandler.makeChanges(fruitStorageDao, fruitName, 100);
        actualCount = db.get(fruitName);
        Assertions.assertEquals(200, actualCount,
                "Does not match the fruits count: expected "
                        + 200 + ", exist " + actualCount);

        // PURCHASE
        actualHandler = operationStrategy.get(OperationType.PURCHASE);
        Assertions.assertEquals(OperationHandlerPurchase.class, actualHandler.getClass());
        actualHandler.makeChanges(fruitStorageDao, fruitName, 50);
        actualCount = db.get(fruitName);
        Assertions.assertEquals(150, actualCount,
                "Does not match the fruits count: expected "
                        + 150 + ", exist " + actualCount);

        // RETURN
        actualHandler = operationStrategy.get(OperationType.RETURN);
        Assertions.assertEquals(OperationHandlerReturn.class, actualHandler.getClass());
        actualHandler.makeChanges(fruitStorageDao, fruitName, 20);
        actualCount = db.get(fruitName);
        Assertions.assertEquals(170, actualCount,
                "Does not match the fruits count: expected "
                        + 170 + ", exist " + actualCount);

    }
}
