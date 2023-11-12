package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.handlers.BalanceOperationHandler;
import core.basesyntax.strategy.handlers.OperationHandler;
import core.basesyntax.strategy.handlers.PurchaseOperationHandler;
import core.basesyntax.strategy.handlers.ReturnOperationHandler;
import core.basesyntax.strategy.handlers.SupplierOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionPerformerImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> handlers;
    private StorageDao storageDao;
    private OperationStrategy operationStrategy;
    private TransactionPerformer transactionPerformer;

    @BeforeEach
    void setUp() {
        storageDao = new FruitDao();
        handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(storageDao));
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplierOperationHandler(storageDao));
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(storageDao));
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler(storageDao));
        operationStrategy = new OperationStrategyImpl(handlers);
        transactionPerformer = new TransactionPerformerImpl(operationStrategy);
    }

    @AfterEach
    void tearDown() {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    void performTransactions_validTransactions_ok() {
        String fruit = "banana";
        int initialQuantity = 50;
        FruitStorage.fruitStorage.put(fruit, initialQuantity);

        final List<FruitTransaction> transactions = new ArrayList<>();
        FruitTransaction transaction1 = new FruitTransaction();
        transaction1.setOperation(FruitTransaction.Operation.BALANCE);
        transaction1.setFruit("banana");
        transaction1.setQuantity(initialQuantity);

        FruitTransaction transaction2 = new FruitTransaction();
        transaction2.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction2.setFruit("banana");
        transaction2.setQuantity(100);

        FruitTransaction transaction3 = new FruitTransaction();
        transaction3.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction3.setFruit("banana");
        transaction3.setQuantity(5);

        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);

        transactionPerformer.performTransactions(transactions);

        int expectedFinalQuantity = 145;

        assertEquals(expectedFinalQuantity, FruitStorage.fruitStorage.get(fruit));
    }

    @Test
    void performTransactions_emptyTransactionsList_ok() {
        String fruit = "apple";
        int initialQuantity = 40;
        FruitStorage.fruitStorage.put(fruit, initialQuantity);

        List<FruitTransaction> transactions = new ArrayList<>();
        transactionPerformer.performTransactions(transactions);
        assertEquals(initialQuantity, FruitStorage.fruitStorage.get(fruit));
    }

    @Test
    void performTransactions_nullTransaction_notOk() {
        String fruit = "banana";
        int initialQuantity = 10;
        FruitStorage.fruitStorage.put(fruit, initialQuantity);

        final List<FruitTransaction> transactions = new ArrayList<>();
        FruitTransaction transaction1 = new FruitTransaction();
        transaction1.setOperation(FruitTransaction.Operation.BALANCE);
        transaction1.setFruit("banana");
        transaction1.setQuantity(initialQuantity);

        transactions.add(transaction1);
        transactions.add(null);

        assertThrows(NullPointerException.class,
                () -> transactionPerformer.performTransactions(transactions));
    }
}
