package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.transaction.BalanceHandlerImpl;
import core.basesyntax.service.transaction.PurchaseHandlerImpl;
import core.basesyntax.service.transaction.ReturnHandlerImpl;
import core.basesyntax.service.transaction.SupplyHandlerImpl;
import core.basesyntax.service.transaction.TransactionHandler;
import core.basesyntax.strategy.TransactionStrategy;
import core.basesyntax.strategy.TransactionStrategyImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProcessDataServiceImplTest {
    private static ProcessDataServiceImpl processDataService;
    private static TransactionStrategy transactionStrategy;
    private List<FruitTransaction> testInput;
    private Map<String, Integer> expected;

    @BeforeAll
    static void beforeAll() {
        processDataService = new ProcessDataServiceImpl();
        Map<FruitTransaction.Operation, TransactionHandler> transactionHandlersMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceHandlerImpl(),
                FruitTransaction.Operation.PURCHASE, new PurchaseHandlerImpl(),
                FruitTransaction.Operation.SUPPLY, new SupplyHandlerImpl(),
                FruitTransaction.Operation.RETURN, new ReturnHandlerImpl()
        );
        transactionStrategy =
                new TransactionStrategyImpl(transactionHandlersMap);
    }

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
        testInput = List.of(
                new FruitTransaction("b", "banana", 20),
                new FruitTransaction("b", "apple", 100),
                new FruitTransaction("s", "banana", 100),
                new FruitTransaction("p", "banana", 13),
                new FruitTransaction("r", "apple", 10),
                new FruitTransaction("p", "apple", 20),
                new FruitTransaction("p", "banana", 5),
                new FruitTransaction("s", "banana", 50)
        );
        expected = Map.of("banana", 152, "apple", 90);
    }

    @Test
    void processDataService_validData_ok() {
        processDataService.processTransactions(testInput, transactionStrategy);
        assertEquals(expected, Storage.fruits);
    }
}

