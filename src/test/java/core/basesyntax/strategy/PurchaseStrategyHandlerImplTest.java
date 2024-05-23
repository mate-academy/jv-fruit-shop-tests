package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.exception.PurchasingException;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseStrategyHandlerImplTest {
    private static FruitDao fruitDao = new FruitDaoImpl();
    private static final String FRUIT_NAME = "apple";
    private static final int FRUIT_QUANTITY = 1;
    private static final FruitTransaction.Operation STRATEGY_PURCHASE =
            FruitTransaction.Operation.PURCHASE;
    private FruitTransaction fruitTransaction;
    private StrategyHandler purchaseStrategyHandler = new PurchaseStrategyHandlerImpl(fruitDao);

    @BeforeAll
    static void beforeAll() {
        fruitDao.getFruitMap().put(FRUIT_NAME, FRUIT_QUANTITY);
    }

    @Test
    void handle_validData_Ok() {
        fruitTransaction = new FruitTransaction(STRATEGY_PURCHASE, FRUIT_NAME, FRUIT_QUANTITY);
        purchaseStrategyHandler.handle(fruitTransaction);
        int expected = 0;
        int actual = fruitDao.getFruitMap().get(FRUIT_NAME);
        assertEquals(expected, actual);
    }

    @Test
    void handle_quantityBiggerThenAmountInStorage_NotOk() {
        int diffQuantity = 2;
        fruitTransaction = new FruitTransaction(STRATEGY_PURCHASE, FRUIT_NAME, diffQuantity);
        PurchasingException exception = assertThrows(PurchasingException.class,
                () -> purchaseStrategyHandler.handle(fruitTransaction));
        String expectedMessage = exception.getMessage();
        String actualMessage = "There are not enough fruits in the store.";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void handle_nullData_NotOk() {
        assertThrows(RuntimeException.class, () -> purchaseStrategyHandler.handle(null));
    }
}
