package core.basesyntax.strategy.activities;

import core.basesyntax.dao.ProductDaoImp;
import core.basesyntax.model.ProductTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SubstractActivitiesHandlerTest {
    private static final String BANANA = "banana";
    private static final int QUANTITY = 20;
    private static Map<String, Integer> storage;
    private static ActivitiesHandler activitiesHandler;
    private static ProductTransaction transaction;

    @BeforeClass
    public static void beforeClass() {
        storage = new HashMap<>();
        activitiesHandler = new SubstractActivitiesHandler(new ProductDaoImp(storage));
        transaction = new ProductTransaction(ProductTransaction.Operation.PURCHASE,
                BANANA, QUANTITY);
    }

    @Test
    public void processSubValidTransaction_ok() {
        storage.put(BANANA, QUANTITY);
        activitiesHandler.process(transaction);
        Assert.assertEquals("Quantity:", 0, storage.get(BANANA).intValue());
    }

    @Test(expected = RuntimeException.class)
    public void processSubMoreThenExist_notOk() {
        storage.put(BANANA, 1);
        activitiesHandler.process(transaction);
    }

    @Test(expected = RuntimeException.class)
    public void processSubFromEmptyStorage_notOk() {
        activitiesHandler.process(transaction);
    }

    @AfterClass
    public static void afterClass() {
        storage.clear();
    }
}
