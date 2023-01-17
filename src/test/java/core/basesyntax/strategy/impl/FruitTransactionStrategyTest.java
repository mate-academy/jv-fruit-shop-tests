package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.FruitTransactionHandler;
import core.basesyntax.strategy.FruitTransactionStrategy;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionStrategyTest {
    private static FruitTransactionStrategy fruitTransactionStrategy;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void init() {
        fruitTransactionStrategy = new FruitTransactionStrategyImpl();
        fruitDao = new FruitDaoImpl();
    }

    @Before
    public void setUp() {
        fruitDao.saveQuantity("banana", 100);
    }

    @Test
    public void getTransaction_chooseBalanceHandler_ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 29);
        FruitTransactionHandler actual = fruitTransactionStrategy
                .getTransaction(fruitTransaction.getOperation());
        FruitTransactionHandler expected = new BalanceHandler();
        Assert.assertEquals(expected.getClass(), actual.getClass());
        actual.handleTransaction(fruitTransaction);
        Assert.assertEquals("Expected quantity 29 for banana in FruitStorage, but was "
                + fruitDao.getQuantityByName("banana"), 29,
                fruitDao.getQuantityByName("banana"));
    }

    @Test
    public void getTransaction_choosePurchaseHandler_ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 29);
        FruitTransactionHandler actual = fruitTransactionStrategy
                .getTransaction(fruitTransaction.getOperation());
        FruitTransactionHandler expected = new PurchaseHandler();
        Assert.assertEquals(expected.getClass(), actual.getClass());
        actual.handleTransaction(fruitTransaction);
        Assert.assertEquals("Expected quantity 71 for banana in FruitStorage, but was "
                + fruitDao.getQuantityByName("banana"), 71,
                fruitDao.getQuantityByName("banana"));
    }

    @Test
    public void getTransaction_chooseSupplyHandler_ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 29);
        FruitTransactionHandler actual = fruitTransactionStrategy
                .getTransaction(fruitTransaction.getOperation());
        FruitTransactionHandler expected = new SupplyHandler();
        Assert.assertEquals(expected.getClass(), actual.getClass());
        actual.handleTransaction(fruitTransaction);
        Assert.assertEquals("Expected quantity 129 for banana in FruitStorage, but was "
                + fruitDao.getQuantityByName("banana"), 129,
                fruitDao.getQuantityByName("banana"));
    }

    @Test
    public void getTransaction_chooseReturnHandler_ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 29);
        FruitTransactionHandler actual = fruitTransactionStrategy
                .getTransaction(fruitTransaction.getOperation());
        FruitTransactionHandler expected = new ReturnHandler();
        Assert.assertEquals(expected.getClass(), actual.getClass());
        actual.handleTransaction(fruitTransaction);
        Assert.assertEquals("Expected quantity 129 for banana in FruitStorage, but was "
                + fruitDao.getQuantityByName("banana"), 129,
                fruitDao.getQuantityByName("banana"));
    }

    @Test
    public void getTransaction_nullOperator_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(null,
                "banana", 29);
        FruitTransactionHandler actual = fruitTransactionStrategy
                .getTransaction(fruitTransaction.getOperation());
        Assert.assertNull(actual);
    }

    @After
    public void tearDown() {
        FruitStorage.storageFruits.clear();
    }
}
