package core.basesyntax.service.calculation;

import core.basesyntax.dao.ShopDao;
import core.basesyntax.dao.ShopDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyCalculatorTest {
    private static TransactionCalculation transactionCalculation;
    private static ShopDao shopDao;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() throws Exception {
        transactionCalculation = new SupplyCalculator();
        shopDao = new ShopDaoImpl();
    }

    @Test
    public void supplyCalculator_Ok() {
        shopDao.add("lemon", 20);
        fruitTransaction = new FruitTransaction("s", "lemon", 20);
        transactionCalculation.calculate(fruitTransaction);
        Integer actual = Storage.storage.get("lemon");
        Integer expected = 40;
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.storage.clear();
    }
}
