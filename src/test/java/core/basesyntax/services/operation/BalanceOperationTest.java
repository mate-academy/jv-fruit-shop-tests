package core.basesyntax.services.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.services.transaction.model.ProductTransaction;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationTest {
    private static ProductDao productDao;

    @BeforeClass
    public static void setProductDao() {
        productDao = new ProductDaoImpl();
    }

    @Test
    public void balanceOperation_Ok() {
        ProductTransaction transaction = new ProductTransaction(
                ProductTransaction.Operation.BALANCE,
                "banana", 100);
        OperationHandler operationHandler = new BalanceOperation(productDao);
        operationHandler.handle(transaction);
        int actual = productDao.get("banana").getCount();
        int expected = 100;
        assertEquals(expected, actual);
    }
}
