package core.basesyntax.service.calculation;

import core.basesyntax.dao.ShopDao;
import core.basesyntax.dao.ShopDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseCalculatorTest {
    private static FruitTransaction transactionBalance;
    private static FruitTransaction transactionSupply;
    private static FruitTransaction transactionPurchase;
    private static ShopDao shopDao;
    private static TransactionCalculation transactionCalculation;

    @BeforeAll
    static void beforeAll() {
        shopDao = new ShopDaoImpl();
        transactionCalculation = new PurchaseCalculator();
    }

    @Test
    void purchaseGreaterThanBalanceSupply_NotOk() {
        transactionBalance = new FruitTransaction("b", "banana", 20);
        transactionSupply = new FruitTransaction("s", "banana", 20);
        transactionPurchase = new FruitTransaction("p", "banana", 100);
        int sum = transactionBalance.getQuantity() + transactionSupply.getQuantity();
        boolean actual = (sum < transactionPurchase.getQuantity());
        Assert.assertTrue(actual);
    }

    @Test
    void purchaseCalculator_Ok() {
        shopDao.add("banana", 100);
        transactionPurchase = new FruitTransaction("p", "banana", 50);
        transactionCalculation.calculate(transactionPurchase);
        Integer actual = Storage.storage.get("banana");
        Integer expected = 50;
        Assert.assertEquals(expected, actual);
    }

    @AfterAll
    static void afterAll() {
        Storage.storage.clear();
    }
}

