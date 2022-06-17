package core.basesyntax.strategy.activities;

import core.basesyntax.dao.ProductDaoImp;
import core.basesyntax.model.ProductTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddActivitiesHandlerTest {
    private static final String BANANA = "banana";
    private static final int QUANTITY = 20;
    private static Map<String, Integer> storage;
    private static ActivitiesHandler activitiesHandler;
    private static ProductTransaction transaction;

    @BeforeClass
    public static void beforeClass() {
        storage = new HashMap<>();
        activitiesHandler = new AddActivitiesHandler(new ProductDaoImp(storage));
        transaction = new ProductTransaction(ProductTransaction.Operation.BALANCE,
                BANANA, QUANTITY);
    }

    @Test
    public void process_addingValidTransaction_ok() {
        activitiesHandler.process(transaction);
        Assert.assertEquals("Quantity of banana: ", QUANTITY, storage.get(BANANA).intValue());
    }
}
