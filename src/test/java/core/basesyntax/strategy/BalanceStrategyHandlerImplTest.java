package core.basesyntax.strategy;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class BalanceStrategyHandlerImplTest {
    private static final String FRUIT_NAME = "apple";
    private static final int FRUIT_QUANTITY = 1;
    private static final int ZERO_SIZE_THRESHOLD = 0;
    private static final FruitTransaction.Operation STRATEGY_BALANCE =
            FruitTransaction.Operation.BALANCE;
    private FruitTransaction fruitTransaction;
    private FruitDao fruitDao = new FruitDaoImpl();
    private BalanceStrategyHandlerImpl balanceStrategyHandler =
            new BalanceStrategyHandlerImpl(fruitDao);

    @Test
    void handle_validData_Ok() {
        fruitTransaction = new FruitTransaction(STRATEGY_BALANCE, FRUIT_NAME, FRUIT_QUANTITY);
        balanceStrategyHandler.handle(fruitTransaction);
        assertTrue(fruitDao.getFruitMap().size() > ZERO_SIZE_THRESHOLD);
    }

    @Test
    void handle_nullData_NotOk() {
        assertThrows(RuntimeException.class, () -> balanceStrategyHandler.handle(null));
    }
}
