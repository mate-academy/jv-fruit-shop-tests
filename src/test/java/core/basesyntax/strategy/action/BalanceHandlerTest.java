package core.basesyntax.strategy.action;

import core.basesyntax.dao.ProductStorageDao;
import core.basesyntax.dao.ProductStorageDaoImpl;
import core.basesyntax.db.ProductStorage;
import core.basesyntax.model.ProductTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerTest {
    private static ProductStorageDao productStorageDao;
    private static ActionHandler actionHandler;
    @SuppressWarnings("FieldCanBeLocal")
    private ProductTransaction transaction;
    private final ProductTransaction.Operation operation = ProductTransaction.Operation.BALANCE;

    @BeforeClass
    public static void beforeClass() {
        productStorageDao = new ProductStorageDaoImpl();
        actionHandler = new BalanceHandler();
    }

    @Before
    public void setUp() {
        ProductStorage.products.put("mango", 200);
    }

    @Test
    public void process_ok() {
        transaction = new ProductTransaction(operation, "mango", 100);
        actionHandler.process(productStorageDao, transaction);
        Assert.assertEquals(Integer.valueOf(100), ProductStorage.products.get("mango"));
    }

    @After
    public void tearDown() {
        ProductStorage.products.clear();
    }
}
