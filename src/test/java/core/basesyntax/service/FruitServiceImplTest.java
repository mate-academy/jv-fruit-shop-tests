package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.operation.OperationHandlerMap;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.strategy.StrategyImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FruitServiceImplTest {
    private static final Strategy strategy =
            new StrategyImpl(OperationHandlerMap.operationHandlerMap);
    private static FruitService fruitService;

    @BeforeAll
    static void beforeAll() {
        fruitService = new FruitServiceImpl(strategy);
    }

    @Test
    public void process_nullValue_notOk() {
        assertThrows(NullPointerException.class, () -> fruitService.process(null));
    }

    @Test
    public void process_emptyList_notOk() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        assertThrows(NullPointerException.class, () -> fruitService.process(fruitTransactions));
    }

    @Test
    public void process_validFruitTransactionList_ok() {
        List<FruitTransaction> fruitTransactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 20));
        Map<String, Integer> expected = Map.of("banana", 30, "apple", 20);
        fruitService.process(fruitTransactions);
        assertEquals(expected, Storage.fruitMap);
    }
}
