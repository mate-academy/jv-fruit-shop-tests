package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class ReturnStrategyHandlerImplTest {
    private FruitTransaction fruitTransaction;
    private FruitDao fruitDao = new FruitDaoImpl();
    private StrategyHandler returnStrategyHandler = new ReturnStrategyHandlerImpl(fruitDao);

    @Test
    void handle_validData_Ok() {
        fruitDao.getFruitMap().put("apple", 1);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 1);
        returnStrategyHandler.handle(fruitTransaction);
        int expected = 2;
        int actual = fruitDao.getFruitMap().get("apple");
        assertEquals(expected, actual);
    }

    @Test
    void handle_nullData_NotOk() {
        assertThrows(RuntimeException.class, () -> returnStrategyHandler.handle(null));
    }
}
