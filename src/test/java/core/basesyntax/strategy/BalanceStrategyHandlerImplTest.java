package core.basesyntax.strategy;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class BalanceStrategyHandlerImplTest {
    private FruitTransaction fruitTransaction;
    private FruitDao fruitDao = new FruitDaoImpl();
    private BalanceStrategyHandlerImpl balanceStrategyHandler =
            new BalanceStrategyHandlerImpl(fruitDao);

    @Test
    void handle_validData_Ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 1);
        balanceStrategyHandler.handle(fruitTransaction);
        assertTrue(fruitDao.getFruitMap().size() > 0);
    }

    @Test
    void handle_nullData_NotOk() {
        assertThrows(RuntimeException.class, () -> balanceStrategyHandler.handle(null));
    }
}
