package core.basesyntax;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.services.operation.*;
import core.basesyntax.services.transaction.model.ProductTransaction;
import org.junit.*;
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
        ProductTransaction transaction = new ProductTransaction(ProductTransaction.Operation.BALANCE,
                "banana", 100);
        OperationHandler operationHandler = new BalanceOperation(productDao);
        operationHandler.handle(transaction);
        int actual = productDao.get("banana").getCount();
        int expected = 100;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void purchaseOperation_Ok() {
        ProductTransaction transaction = new ProductTransaction(ProductTransaction.Operation.PURCHASE,
                "banana", 50);
        OperationHandler o = new PurchaseOperation(productDao);
        o.handle(transaction);
        int actual = productDao.get("banana").getCount();
        int expected = 50;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void returnOperation() {
        ProductTransaction transaction = new ProductTransaction(ProductTransaction.Operation.RETURN,
                "banana", 10);
        OperationHandler operationHandler = new ReturnOperation(productDao);
        operationHandler.handle(transaction);
        int actual = productDao.get("banana").getCount();
        int expected = 60;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void supplyOperation_Ok() {
        ProductTransaction transaction = new ProductTransaction(ProductTransaction.Operation.SUPPLY,
                "banana", 90);
        OperationHandler operationHandler = new SupplyOperation(productDao);
        operationHandler.handle(transaction);
        int actual = productDao.get("banana").getCount();
        int expected = 150;
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void clearDao(){
        Storage.products.clear();
    }

}
