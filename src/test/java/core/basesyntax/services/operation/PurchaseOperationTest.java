package core.basesyntax.services.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Product;
import core.basesyntax.services.transaction.model.ProductTransaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationTest {
    private static ProductDao productDao;

    @BeforeClass
    public static void setProductDao() {
        productDao = new ProductDaoImpl();
        Storage.products.add(new Product("banana", 100));
    }

    @Test
    public void handle_purchaseOperation_ok() {
        ProductTransaction transaction = new ProductTransaction(
                ProductTransaction.Operation.PURCHASE,
                "banana", 50);
        OperationHandler o = new PurchaseOperation(productDao);
        o.handle(transaction);
        int actual = productDao.get("banana").getCount();
        int expected = 50;
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void clearDao() {
        Storage.products.clear();
    }
}
