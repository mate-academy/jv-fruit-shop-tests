package core.basesyntax.strategy.action;

import core.basesyntax.dao.ProductStorageDao;
import core.basesyntax.dao.ProductStorageDaoImpl;
import core.basesyntax.db.ProductStorage;
import core.basesyntax.exception.ActionNegativeQuantityException;
import core.basesyntax.exception.ActionProductNotFoundException;
import core.basesyntax.model.ProductTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static ProductStorageDao productStorageDao;
    private static ActionHandler actionHandler;
    private ProductTransaction transaction;
    private final ProductTransaction.Operation operation = ProductTransaction.Operation.PURCHASE;

    @BeforeClass
    public static void beforeClass() {
        productStorageDao = new ProductStorageDaoImpl();
        actionHandler = new PurchaseHandler();
    }

    @Before
    public void setUp() {
        ProductStorage.products.put("mango", 200);
    }

    @Test
    public void process_productExist_ok() {
        transaction = new ProductTransaction(operation, "mango", 50);
        actionHandler.process(productStorageDao, transaction);
        Assert.assertEquals(Integer.valueOf(150), ProductStorage.products.get("mango"));
    }

    @Test(expected = ActionProductNotFoundException.class)
    public void process_productNotExist_notOk() {
        transaction = new ProductTransaction(operation, "apple", 50);
        actionHandler.process(productStorageDao, transaction);
    }

    @Test(expected = ActionNegativeQuantityException.class)
    public void process_negativeResult_notOk() {
        transaction = new ProductTransaction(operation, "mango", 300);
        actionHandler.process(productStorageDao, transaction);
    }

    @After
    public void tearDown() {
        ProductStorage.products.clear();
    }
}
