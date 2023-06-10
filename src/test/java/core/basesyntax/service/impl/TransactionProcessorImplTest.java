package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.FruitService;
import core.basesyntax.strategy.OperationHandlerStrategy;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.FruitServiceImpl;
import core.basesyntax.strategy.impl.OperationHandlerStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionProcessorImplTest {
    private static TransactionProcessorImpl transactionProcessor;
    private StorageDao storage;

    @BeforeEach
     void setUp() {
        storage = new StorageDaoImpl();
        FruitService fruitService = new FruitServiceImpl(storage);
        OperationHandlerStrategy strategy = new OperationHandlerStrategyImpl(
                Map.of(
                    Transaction.Operation.BALANCE, new BalanceOperationHandler(fruitService),
                    Transaction.Operation.SUPPLY, new SupplyOperationHandler(fruitService),
                    Transaction.Operation.RETURN, new ReturnOperationHandler(fruitService),
                    Transaction.Operation.PURCHASE, new PurchaseOperationHandler(fruitService)
                )
        );
        transactionProcessor = new TransactionProcessorImpl(strategy);
    }

    @Test
    void process_emptyTransactionList_ok() {
        transactionProcessor.process(Collections.emptyList());
        assertTrue(storage.getAll().isEmpty());
    }

    @Test
    void process_nonEmptyTransactionList_ok() {
        List<Transaction> fruitTransaction = List.of(
                new Transaction(
                        Transaction.Operation.BALANCE,
                        "banana",
                        120
                ),
                new Transaction(
                        Transaction.Operation.PURCHASE,
                        "banana",
                        20
                ),
                new Transaction(
                        Transaction.Operation.SUPPLY,
                        "apple",
                        30
                ),
                new Transaction(
                        Transaction.Operation.RETURN,
                        "apple",
                        20
                )
        );
        transactionProcessor.process(fruitTransaction);
        Map<String, Integer> expected = Map.of(
                "banana", 100,
                "apple", 50
        );
        Map<String, Integer> actual = storage.getAll();
        assertEquals(expected, actual);
    }
}
