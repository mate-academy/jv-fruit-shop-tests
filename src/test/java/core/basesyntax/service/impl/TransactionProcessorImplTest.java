package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.StrategyService;
import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionProcessorImplTest {
    private static final String KEY_TO_STORAGE_MAP = "apple";

    private TransactionProcessor transactionProcessor;
    private StrategyService strategyService = new StrategyServiceImpl(
            Map.of(Operation.BALANCE, new BalanceOperation(),
                    Operation.PURCHASE, new PurchaseOperation(),
                    Operation.SUPPLY, new SupplyOperation(),
                    Operation.RETURN, new ReturnOperation()));

    @BeforeEach
    void setUp() {
        transactionProcessor = new TransactionProcessorImpl(strategyService);
        Storage.fruitsStorage.clear();
    }

    @Test
    void process_validData_ok() {
        Storage.fruitsStorage.put(KEY_TO_STORAGE_MAP, 10);
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.BALANCE, "apple", 10),
                new FruitTransaction(Operation.SUPPLY, "apple", 5),
                new FruitTransaction(Operation.RETURN, "apple", 4),
                new FruitTransaction(Operation.PURCHASE, "apple", 4)
                );
        transactionProcessor.process(transactions);
        assertEquals(15, Storage.fruitsStorage.get(KEY_TO_STORAGE_MAP));
    }

    @Test
    void process_EmptyData_notOk() {
        RuntimeException expectedMessage = assertThrows(RuntimeException.class,
                () -> transactionProcessor.process(Arrays.asList()));
        assertEquals("Transaction list is empty", expectedMessage.getMessage());
    }
}
