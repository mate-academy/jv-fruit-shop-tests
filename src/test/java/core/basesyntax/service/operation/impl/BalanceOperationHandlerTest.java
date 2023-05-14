package core.basesyntax.service.operation.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.dao.FruitShopDao;
import core.basesyntax.dao.FruitShopDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final String FRUIT_BANANA = "banana";
    private static final int VALID_QUANTITY = 30;
    private static final Integer EXPECTED_QUANTITY = 30;
    private static final FruitTransaction.Operation VALID_OPERATION =
            FruitTransaction.Operation.BALANCE;
    private static BalanceOperationHandler balanceOperationHandler;
    private static FruitShopDao fruitShopDao;
    private static FruitTransaction fruitTransactionOk;
    private static FruitTransaction fruitTransactionNull;

    @BeforeClass
    public static void beforeClass() {
        fruitTransactionOk = new FruitTransaction(VALID_QUANTITY,
                VALID_OPERATION, FRUIT_BANANA);
        fruitTransactionNull = null;

        fruitShopDao = new FruitShopDaoImpl();
        balanceOperationHandler = new BalanceOperationHandler(fruitShopDao);
    }

    @Test(expected = RuntimeException.class)
    public void balanceOperation_null_notOk() {
        balanceOperationHandler.operation(fruitTransactionNull);
        fail("Expected " + RuntimeException.class.getName() + " to be thrown for null data but "
                + "it's wasn't");
    }

    @Test
    public void balanceOperation_ok() {
        balanceOperationHandler.operation(fruitTransactionOk);
        assertEquals(Storage.fruits.get(FRUIT_BANANA), EXPECTED_QUANTITY);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}
