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
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
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
        String fruit = BANANA;
        int initialQuantity = 50;
        FruitStorage.fruitStorage.put(fruit, initialQuantity);

        final List<FruitTransaction> transactions = new ArrayList<>();
        FruitTransaction balanceTransaction = new FruitTransaction();
        balanceTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        balanceTransaction.setFruit(BANANA);
        balanceTransaction.setQuantity(initialQuantity);

        FruitTransaction supplyTransaction = new FruitTransaction();
        supplyTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        supplyTransaction.setFruit(BANANA);
        supplyTransaction.setQuantity(100);

        FruitTransaction purchaseTransaction = new FruitTransaction();
        purchaseTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        purchaseTransaction.setFruit(BANANA);
        purchaseTransaction.setQuantity(5);

        transactions.add(balanceTransaction);
        transactions.add(supplyTransaction);
        transactions.add(purchaseTransaction);

        transactionPerformer.performTransactions(transactions);

        int expectedFinalQuantity = 145;

        assertEquals(expectedFinalQuantity, FruitStorage.fruitStorage.get(fruit));
    }

    @Test
    void performTransactions_emptyTransactionsList_ok() {
        String fruit = APPLE;
        int initialQuantity = 40;
        FruitStorage.fruitStorage.put(fruit, initialQuantity);

        List<FruitTransaction> transactions = new ArrayList<>();
        transactionPerformer.performTransactions(transactions);
        assertEquals(initialQuantity, FruitStorage.fruitStorage.get(fruit));
    }

    @Test
    void performTransactions_nullTransaction_notOk() {
        String fruit = BANANA;
        int initialQuantity = 10;
        FruitStorage.fruitStorage.put(fruit, initialQuantity);

        final List<FruitTransaction> transactions = new ArrayList<>();
        FruitTransaction balanceTransaction = new FruitTransaction();
        balanceTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        balanceTransaction.setFruit(BANANA);
        balanceTransaction.setQuantity(initialQuantity);

        transactions.add(balanceTransaction);
        transactions.add(null);

        assertThrows(IllegalArgumentException.class,
                () -> transactionPerformer.performTransactions(transactions));
    }
}
