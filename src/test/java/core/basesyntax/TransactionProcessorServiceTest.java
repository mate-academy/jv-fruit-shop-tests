package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.services.impl.TransactionProcessorServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionProcessorServiceTest {
    private TransactionProcessorServiceImpl transactionProcessorService;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(storage));
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(storage));
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(storage));
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler(storage));
        OperationStrategy operationStrategy = new OperationStrategyImpl(handlers);
        transactionProcessorService = new TransactionProcessorServiceImpl(operationStrategy);
    }

    @Test
    public void processTransactions_validTransactions_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,"orange", 200),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 50)
        );
        transactionProcessorService.processTransactions(transactions);
        assertEquals(50, storage.getQuantity("apple"));
        assertEquals(150, storage.getQuantity("orange"));
    }

    @Test
    public void processTransactions_invalidTransaction_throwsException() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 100)
        );
        assertThrows(RuntimeException.class, ()
                -> transactionProcessorService.processTransactions(transactions));
    }
}
