package core.basesyntax.strategy;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceStrategyHandlerImplTest {
    private static final int ZERO_SIZE_THRESHOLD = 0;
    private BalanceStrategyHandlerImpl balanceStrategyHandler;

    @BeforeEach
    void setUp() {
        FruitDao fruitDao = new FruitDaoImpl();
        balanceStrategyHandler = new BalanceStrategyHandlerImpl(fruitDao);
    }

    @Test
    void handle_validData_Ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 1);
        balanceStrategyHandler.handle(fruitTransaction);
        assertTrue(Storage.FRUITS.size() > ZERO_SIZE_THRESHOLD);
    }

    @Test
    void handle_nullData_NotOk() {
        assertThrows(RuntimeException.class, () -> balanceStrategyHandler.handle(null));
    }
}
