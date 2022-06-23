package core.basesyntax.service.calculation;

import core.basesyntax.dao.ShopDao;
import core.basesyntax.dao.ShopDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyCalculatorTest {
    private static TransactionCalculation transactionCalculation;
    private static ShopDao shopDao;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        transactionCalculation = new SupplyCalculator();
        shopDao = new ShopDaoImpl();
    }

    @Test
    void supplyCalculator_Ok() {
        shopDao.add("lemon", 20);
        fruitTransaction = new FruitTransaction("s", "lemon", 20);
        transactionCalculation.calculate(fruitTransaction);
        Integer actual = Storage.storage.get("lemon");
        Integer expected = 40;
        Assert.assertEquals(expected, actual);
    }

    @AfterAll
    static void afterAll() {
        Storage.storage.clear();
    }
}
