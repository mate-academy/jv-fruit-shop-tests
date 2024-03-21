package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.storage.Storage;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitStrategyTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private FruitStrategy fruitStrategy;

    @BeforeEach
    void setUp() {
        FruitDao fruitDao = new FruitDaoImpl();
        Map<Operation, OperationHandler> strategyMap = Map.of(
                Operation.BALANCE, new BalanceStrategy(fruitDao),
                Operation.PURCHASE, new DecreaseStrategy(fruitDao),
                Operation.SUPPLY, new IncreaseStrategy(fruitDao),
                Operation.RETURN, new IncreaseStrategy(fruitDao)
        );
        fruitStrategy = new FruitStrategy(strategyMap);
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void processData_validData_ok() {
        List<FruitTransaction> fruitTransactions = List.of(
                new FruitTransaction(Operation.BALANCE, BANANA, 100),
                new FruitTransaction(Operation.BALANCE, APPLE, 80),
                new FruitTransaction(Operation.PURCHASE, BANANA, 10),
                new FruitTransaction(Operation.SUPPLY, APPLE, 75),
                new FruitTransaction(Operation.RETURN, BANANA, 5)
        );
        fruitStrategy.processData(fruitTransactions);
        Map<String, Integer> actual = Storage.STORAGE;
        Map<String, Integer> expected = Map.of(APPLE, 155,
                BANANA, 95);
        assertEquals(expected, actual);
    }
}
