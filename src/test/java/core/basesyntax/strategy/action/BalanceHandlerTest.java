package core.basesyntax.strategy.action;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.db.ProductStorage;
import core.basesyntax.model.ProductTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerTest {
    private static ProductDao productDao;
    private static ActionHandler actionHandler;
    @SuppressWarnings("FieldCanBeLocal")
    private ProductTransaction transaction;
    private final ProductTransaction.Operation operation = ProductTransaction.Operation.BALANCE;

    @BeforeClass
    public static void beforeClass() {
        productDao = new ProductDaoImpl();
        actionHandler = new BalanceHandler();
    }

    @Before
    public void setUp() {
        ProductStorage.products.put("mango", 200);
    }

    @Test
    public void process_ok() {
        transaction = new ProductTransaction(operation, "mango", 100);
        actionHandler.process(productDao, transaction);
        Assert.assertEquals(Integer.valueOf(100), ProductStorage.products.get("mango"));
    }

    @After
    public void tearDown() {
        ProductStorage.products.clear();
    }
}
