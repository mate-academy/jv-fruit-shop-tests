package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.FruitOperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperationStrategy;
import core.basesyntax.strategy.impl.DecreaseQuantityStrategy;
import core.basesyntax.strategy.impl.IncreaseQuantityStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitServiceImplTest {
    private static FruitService fruitService;

    @BeforeAll
    public static void setUp() {
        FruitDao fruitDao = new FruitDaoImpl();
        Map<Operation, FruitOperationStrategy> strategyMap =
                Map.of(Operation.BALANCE, new BalanceOperationStrategy(fruitDao),
                Operation.SUPPLY, new IncreaseQuantityStrategy(fruitDao),
                Operation.RETURN, new IncreaseQuantityStrategy(fruitDao),
                Operation.PURCHASE, new DecreaseQuantityStrategy(fruitDao));
        fruitService = new FruitServiceImpl(strategyMap);
    }

    @Test
    void processTransactions_validList_ok() {
        String banana = "banana";
        String apple = "apple";
        List<FruitTransaction> transactions =
                List.of(new FruitTransaction(Operation.BALANCE, banana, 10),
                        new FruitTransaction(Operation.BALANCE, apple, 5),
                        new FruitTransaction(Operation.RETURN, banana, 3),
                        new FruitTransaction(Operation.PURCHASE, apple, 2),
                        new FruitTransaction(Operation.SUPPLY, apple, 7));
        fruitService.processTransactions(transactions);

        Map<String, Integer> expected = Map.of(banana, 13, apple, 10);
        Map<String, Integer> actual = FruitStorage.getFruits();
        assertEquals(expected, actual, "Error in processing transactions");

        FruitStorage.getFruits().clear();

        transactions = new ArrayList<>();
        fruitService.processTransactions(transactions);
        expected = new HashMap<>();
        actual = FruitStorage.getFruits();
        assertEquals(expected, actual, "Error in processing transactions");
    }

    @Test
    public void processTransactions_nullListOfTransactions_notOk() {
        assertThrows(RuntimeException.class, () -> fruitService.processTransactions(null),
                "RuntimeException should be thrown if list of transactions is null!");
    }
}
