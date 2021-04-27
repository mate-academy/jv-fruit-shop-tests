package core.basesyntax.service.operation;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.model.Product;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationHandlerIncreaseTest {
    private static OperationHandler operation;
    private static ProductDao productDao;

    @BeforeClass
    public static void beforeClass() throws Exception {
        productDao = new ProductDaoImpl();
        operation = new OperationIncreaseHandler(productDao);
    }

    @After
    public void tearDown() throws Exception {
        productDao.getAll().clear();
    }

    @Test
    public void supplyAmountInEmptyStorage_isOk() {
        Product banana = new Product("banana");
        int exceptedAmount = 100;
        int actualAmount = operation.apply(100, banana);
        Assert.assertEquals(exceptedAmount, actualAmount);
    }

    @Test
    public void supplyAmountInStorage_isOk() {
        saveToStorage();
        Product banana = new Product("banana");
        int exceptedAmount = 202;
        int actualAmount = operation.apply(50, banana);
        Assert.assertEquals(exceptedAmount, actualAmount);
    }

    private static void saveToStorage() {
        Product banana = new Product("banana");
        Product apple = new Product("apple");
        productDao.add(banana, 152);
        productDao.add(apple, 90);
    }
}
