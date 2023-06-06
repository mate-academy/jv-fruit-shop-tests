package core.basesyntax.service.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.MapStorage;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.impl.FruitTransactionProcessorImpl;
import core.basesyntax.service.strategy.OperationHandlerStrategy;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static FruitTransactionProcessorImpl fruitTransactionProcessor;
    private static Storage storage;

    @BeforeAll
    static void setUp() {
        storage = new MapStorage();
        FruitService fruitService = new FruitServiceImpl(storage);
        OperationHandlerStrategy strategy = new OperationHandlerStrategyImpl(
                Map.of(FruitTransaction.Operation.BALANCE,
                        new BalanceOperationHandler(fruitService)
                )
        );
        fruitTransactionProcessor = new FruitTransactionProcessorImpl(
                strategy
        );
    }

    @Test
    void handle_balanceOperationValidInput_ok() {
        List<FruitTransaction> fruitTransactions = List.of(
                new FruitTransaction(
                        FruitTransaction.Operation.BALANCE,
                        "apple",
                        10
                ),
                new FruitTransaction(
                        FruitTransaction.Operation.BALANCE,
                        "orange",
                        54
                )
        );
        fruitTransactionProcessor.process(fruitTransactions);
        Map<String, Integer> expected = Map.of(
                "apple", 10,
                "orange", 54
        );
        Map<String, Integer> actual = storage.getAll();
        assertEquals(expected, actual);
    }
}
