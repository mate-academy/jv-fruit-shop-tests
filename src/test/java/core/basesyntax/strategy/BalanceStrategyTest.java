package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.storage.Storage;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceStrategyTest {
    private static OperationHandler operationHandler;
    private static FruitDao fruitDao;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new BalanceStrategy(new FruitDaoImpl());
        fruitDao = new FruitDaoImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void apply_validInput_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.BALANCE, "banana", 100);
        operationHandler.apply(fruitTransaction);
        Map<String, Integer> expected = Map.of(fruitTransaction.fruitName(),
                fruitTransaction.quantity());
        Map<String, Integer> actual = fruitDao.getFruits();
        assertEquals(expected, actual);
    }
}
