package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionProcessorTest {
    private static TransactionProcessor processor;
    private List<FruitTransaction> transactions;

    @BeforeAll
    public static void setUpAll() {
        Map<FruitTransaction.Operation, OperationHandler> operationStrategies = new HashMap<>();
        operationStrategies.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        processor = new FruitTransactionProcessor(new OperationStrategy(operationStrategies));
    }

    @BeforeEach
    public void setUp() {
        transactions = new ArrayList<>();
    }

    @AfterEach
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void processAll_presentTransactions_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        transactions.add(fruitTransaction);

        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(100);
        transactions.add(fruitTransaction);

        assertDoesNotThrow(() -> processor.processAll(transactions));
        assertNotNull(Storage.fruits.get("banana"));
        assertNotNull(Storage.fruits.get("apple"));
    }

    @Test
    public void processAll_noTransactions_notOk() {
        assertThrows(RuntimeException.class,() -> processor.processAll(transactions));
    }
}
