package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.services.operation.BalanceOperation;
import core.basesyntax.services.operation.OperationHandler;
import core.basesyntax.services.operation.PurchaseOperation;
import core.basesyntax.services.operation.ReturnOperation;
import core.basesyntax.services.operation.SupplyOperation;
import core.basesyntax.services.transaction.model.ProductTransaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OperationsTests {
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

    @Test
    public void purchaseOperation_Ok() {
        ProductTransaction transaction = new ProductTransaction(
                ProductTransaction.Operation.PURCHASE,
                "banana", 50);
        OperationHandler o = new PurchaseOperation(productDao);
        o.handle(transaction);
        int actual = productDao.get("banana").getCount();
        int expected = 50;
        assertEquals(expected, actual);
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

    @Test
    public void supplyOperation_Ok() {
        ProductTransaction transaction = new ProductTransaction(ProductTransaction.Operation.SUPPLY,
                "banana", 90);
        OperationHandler operationHandler = new SupplyOperation(productDao);
        operationHandler.handle(transaction);
        int actual = productDao.get("banana").getCount();
        int expected = 150;
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void clearDao() {
        Storage.products.clear();
    }

}
