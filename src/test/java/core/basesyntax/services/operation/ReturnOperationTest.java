package core.basesyntax.services.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.services.transaction.model.ProductTransaction;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationTest {
    private static ProductDao productDao;

    @BeforeClass
    public static void setProductDao() {
        productDao = new ProductDaoImpl();
    }

    @Test
    public void returnOperation() {
        ProductTransaction transaction = new ProductTransaction(ProductTransaction.Operation.RETURN,
                "banana", 10);
        OperationHandler operationHandler = new ReturnOperation(productDao);
        operationHandler.handle(transaction);
        int actual = productDao.get("banana").getCount();
        int expected = 60;
        assertEquals(expected, actual);
    }
}
