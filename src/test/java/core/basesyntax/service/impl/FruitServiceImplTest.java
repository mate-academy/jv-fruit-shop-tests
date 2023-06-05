package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.handler.impl.BalanceHandler;
import core.basesyntax.strategy.handler.impl.PurchaseHandler;
import core.basesyntax.strategy.handler.impl.ReturnHandler;
import core.basesyntax.strategy.handler.impl.SupplyHandler;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitServiceImplTest {
    private static FruitService fruitService;
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void beforeAll() {
        fruitService = new FruitServiceImpl();
        operationStrategy = new OperationStrategy(
                Map.of(
                        FruitTransaction.Operation.BALANCE, new BalanceHandler(),
                        FruitTransaction.Operation.PURCHASE, new PurchaseHandler(),
                        FruitTransaction.Operation.RETURN, new ReturnHandler(),
                        FruitTransaction.Operation.SUPPLY, new SupplyHandler()
                )
        );
    }

    @Test
    void writeActionToStorage_noTransactions_ok() {
        fruitService.writeActionToStorage(Collections.emptyList(), operationStrategy);
        assertTrue(Storage.fruitStorage.isEmpty());
    }

    @Test
    void writeActionToStorage_transactionsIsPresent_ok() {
        List<FruitTransaction> fruitTransactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50)
        );
        fruitService.writeActionToStorage(fruitTransactions, operationStrategy);
        Map<String, Integer> expected = Map.of(
                "banana", 152,
                "apple", 90
        );
        Map<String, Integer> actual = Storage.fruitStorage;
        assertEquals(expected, actual);
    }

    @AfterEach
    void clear() {
        Storage.fruitStorage.clear();
    }
}
