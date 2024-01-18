package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperationHandlerImpl;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandlerImpl;
import core.basesyntax.strategy.impl.ReturnOperationHandlerImpl;
import core.basesyntax.strategy.impl.SupplyOperationHandlerImpl;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionServiceImplTest {
    private static OperationStrategy operationStrategy;
    private final TransactionService transactionService =
            new TransactionServiceImpl(operationStrategy);
    private List<FruitTransaction> transactions;

    @BeforeAll
    static void beforeAll() {
        operationStrategy = new OperationStrategyImpl(
                Map.of(FruitTransaction.Operation.BALANCE, new BalanceOperationHandlerImpl(),
                        FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandlerImpl(),
                        FruitTransaction.Operation.SUPPLY, new SupplyOperationHandlerImpl(),
                        FruitTransaction.Operation.RETURN, new ReturnOperationHandlerImpl()));
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void processTransactions_Ok() {
        transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 50),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 20));
        transactionService.processTransactions(transactions);
        int actual = Storage.fruits.get("banana");
        int expected = 90;
        assertEquals(expected, actual);
    }

    @Test
    void processEmptyList_Ok() {
        transactionService.processTransactions(Collections.emptyList());
        assertTrue(Storage.fruits.isEmpty());
    }
}
