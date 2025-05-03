package core.basesyntax.service.calculation;

import core.basesyntax.dao.ShopDao;
import core.basesyntax.dao.ShopDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseCalculatorTest {
    private static FruitTransaction transactionBalance;
    private static FruitTransaction transactionSupply;
    private static FruitTransaction transactionPurchase;
    private static ShopDao shopDao;
    private static TransactionCalculation transactionCalculation;

    @BeforeClass
    public static void beforeClass() {
        shopDao = new ShopDaoImpl();
        transactionCalculation = new PurchaseCalculator();
    }

    @Test
    public void purchaseGreaterThanBalanceSupply_NotOk() {
        transactionBalance = new FruitTransaction("b", "banana", 20);
        transactionSupply = new FruitTransaction("s", "banana", 20);
        transactionPurchase = new FruitTransaction("p", "banana", 100);
        int sum = transactionBalance.getQuantity() + transactionSupply.getQuantity();
        boolean actual = (sum < transactionPurchase.getQuantity());
        Assert.assertTrue(actual);
    }

    @Test
    public void calculate_validTransaction_ok() {
        shopDao.add("banana", 100);
        transactionPurchase = new FruitTransaction("p", "banana", 50);
        transactionCalculation.calculate(transactionPurchase);
        Integer actual = Storage.storage.get("banana");
        Integer expected = 50;
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.storage.clear();
    }
}
