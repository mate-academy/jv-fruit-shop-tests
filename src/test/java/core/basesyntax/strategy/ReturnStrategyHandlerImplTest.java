package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class ReturnStrategyHandlerImplTest {
    private final FruitDao fruitDao = new FruitDaoImpl();
    private final StrategyHandler returnStrategyHandler = new ReturnStrategyHandlerImpl(fruitDao);

    @Test
    void handle_validData_ok() {
        Storage.FRUITS.put("apple", 1);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 1);
        returnStrategyHandler.handle(fruitTransaction);
        int expected = 2;
        int actual = Storage.FRUITS.get("apple");
        assertEquals(expected, actual);
    }

    @Test
    void handle_nullData_notOk() {
        assertThrows(RuntimeException.class, () -> returnStrategyHandler.handle(null));
    }
}
