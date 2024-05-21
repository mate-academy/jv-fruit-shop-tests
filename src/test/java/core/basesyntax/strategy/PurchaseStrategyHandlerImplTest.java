package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class PurchaseStrategyHandlerImplTest {
    private FruitTransaction fruitTransaction;
    private FruitDao fruitDao = new FruitDaoImpl();
    private StrategyHandler purchaseStrategyHandler = new PurchaseStrategyHandlerImpl(fruitDao);

    @Test
    void handle_validData_Ok() {
        fruitDao.getFruitMap().put("apple", 1);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 1);
        purchaseStrategyHandler.handle(fruitTransaction);
        int actual = fruitDao.getFruitMap().get("apple");
        assertEquals(0, actual);
    }

    @Test
    void handle_nullData_NotOk() {
        assertThrows(RuntimeException.class, () -> purchaseStrategyHandler.handle(null));
    }
}
