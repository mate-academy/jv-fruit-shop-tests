package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class SupplyStrategyHandlerImplTest {

    private static final String FRUIT_NAME = "apple";
    private static final int FRUIT_QUANTITY = 1;
    private static final FruitTransaction.Operation STRATEGY_SUPPLY =
            FruitTransaction.Operation.SUPPLY;
    private FruitTransaction fruitTransaction;
    private FruitDao fruitDao = new FruitDaoImpl();
    private StrategyHandler supplyStrategyHandler = new SupplyStrategyHandlerImpl(fruitDao);

    @Test
    void handle_validData_Ok() {
        fruitDao.getFruitMap().put(FRUIT_NAME, FRUIT_QUANTITY);
        fruitTransaction = new FruitTransaction(STRATEGY_SUPPLY, FRUIT_NAME, FRUIT_QUANTITY);
        supplyStrategyHandler.handle(fruitTransaction);
        int expected = 2;
        int actual = fruitDao.getFruitMap().get(FRUIT_NAME);
        assertEquals(expected, actual);
    }

    @Test
    void handle_nullData_NotOk() {
        assertThrows(RuntimeException.class, () -> supplyStrategyHandler.handle(null));
    }

}
