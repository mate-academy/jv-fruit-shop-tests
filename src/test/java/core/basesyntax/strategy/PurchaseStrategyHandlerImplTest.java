package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.PurchasingException;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseStrategyHandlerImplTest {
    private static FruitDao fruitDao;
    private static StrategyHandler purchaseStrategyHandler;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        Storage.FRUITS.put("apple", 1);
        fruitDao = new FruitDaoImpl();
        purchaseStrategyHandler = new PurchaseStrategyHandlerImpl(fruitDao);
    }

    @Test
    void handle_validData_ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 1);
        purchaseStrategyHandler.handle(fruitTransaction);
        int expected = 0;
        int actual = Storage.FRUITS.get("apple");
        assertEquals(expected, actual);
    }

    @Test
    void handle_quantityBiggerThenAmountInStorage_notOk() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 2);
        PurchasingException exception = assertThrows(PurchasingException.class,
                () -> purchaseStrategyHandler.handle(fruitTransaction));
        String expectedMessage = exception.getMessage();
        String actualMessage = "There are not enough fruits in the store.";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void handle_nullData_notOk() {
        assertThrows(RuntimeException.class, () -> purchaseStrategyHandler.handle(null));
    }
}
