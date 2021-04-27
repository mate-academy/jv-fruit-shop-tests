package core.basesyntax.service.operation;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.exception.EmptyStorageException;
import core.basesyntax.exception.ValueFormatException;
import core.basesyntax.model.Product;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationDecreaseHandlerTest {
    private static OperationHandler operation;
    private static ProductDao productDao;

    @BeforeClass
    public static void beforeClass() throws Exception {
        productDao = new ProductDaoImpl();
        operation = new OperationDecreaseHandler(productDao);
    }

    @After
    public void tearDown() throws Exception {
        productDao.getAll().clear();
    }

    @Test
    public void decreaseAmount_isOk() {
        saveToStorage();
        Product banana = new Product("banana");
        int exceptedAmount = 102;
        int actualAmount = operation.apply(50, banana);
        Assert.assertEquals(exceptedAmount, actualAmount);
    }

    @Test(expected = ValueFormatException.class)
    public void purchaseProductMoreThanInStorage_notOk() {
        saveToStorage();
        Product apple = new Product("apple");
        operation.apply(100, apple);
    }

    @Test(expected = EmptyStorageException.class)
    public void purchaseProductInEmptyStorage_notOk() {
        Product apple = new Product("apple");
        operation.apply(100,apple);
    }

    private static void saveToStorage() {
        Product banana = new Product("banana");
        Product apple = new Product("apple");
        productDao.add(banana, 152);
        productDao.add(apple, 90);
    }
}
