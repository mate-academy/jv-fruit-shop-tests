package core.basesyntax.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitShopDao;
import core.basesyntax.dao.FruitShopDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SubtractionOperationHandlerTest {
    private static final int EXPECTED = -132;
    private static final String KEY = "banana";
    private static FruitShopDao fruitShopDao;
    private static OperationHandler OperationHandler;
    private static FruitTransaction firstFruitTransaction;
    private static FruitTransaction secondFruitTransaction;
    private static FruitTransaction thirdFruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        fruitShopDao = new FruitShopDaoImpl();
        fruitShopDao.put(KEY, 0);
        OperationHandler = new SubtractionOperationHandler(fruitShopDao);
        firstFruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, KEY, 100);
        secondFruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, KEY, 12);
        thirdFruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, KEY, 20);
    }

    @Test
    public void handle() {
        OperationHandler.handle(firstFruitTransaction);
        OperationHandler.handle(secondFruitTransaction);
        OperationHandler.handle(thirdFruitTransaction);
        assertEquals(EXPECTED, fruitShopDao.get(KEY));
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}
