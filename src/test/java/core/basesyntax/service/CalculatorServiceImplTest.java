package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.CalculatorServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.StrategyStorageImpl;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class CalculatorServiceImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int APPLE_QUANTITY = 50;
    private static final int BANANA_QUANTITY = 20;
    private static final int PURCHASE_QUANTITY = 10;
    private static CalculatorService calculatorService;

    @BeforeClass
    public static void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        StrategyStorageImpl strategyStorage = new StrategyStorageImpl();
        strategyStorage.setHandlers(handlers);
        calculatorService = new CalculatorServiceImpl(strategyStorage);
    }

    @Test
    public void calculate_validTransactions_ok() {
        FruitTransaction balanceTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, APPLE_QUANTITY);
        FruitTransaction supplyTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, BANANA_QUANTITY);
        FruitTransaction purchaseTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        APPLE,
                        PURCHASE_QUANTITY);
        FruitTransaction returnTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, BANANA, 5);
        List<FruitTransaction> transactions =
                Arrays.asList(balanceTransaction,
                        supplyTransaction,
                        purchaseTransaction,
                        returnTransaction);
        calculatorService.calculate(transactions);
        assertEquals(APPLE_QUANTITY - PURCHASE_QUANTITY, Storage.storage.get(APPLE).intValue());
        assertEquals(BANANA_QUANTITY + 5, Storage.storage.get(BANANA).intValue());
    }

    @Test(expected = RuntimeException.class)
    public void calculate_purchaseWithInsufficientStock_noOk() {
        FruitTransaction balanceTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        APPLE,
                        APPLE_QUANTITY);
        FruitTransaction purchaseTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        APPLE,
                        60);
        List<FruitTransaction> transactions = Arrays.asList(balanceTransaction,
                purchaseTransaction);
        calculatorService.calculate(transactions);
    }

    @Test(expected = RuntimeException.class)
    public void calculate_nullTransactionList_notOk() {
        List<FruitTransaction> transactions = null;
        calculatorService.calculate(transactions);
    }

    @Test(expected = RuntimeException.class)
    public void calculate_emptyTransactionList_notOk() {
        List<FruitTransaction> transactions = Collections.emptyList();
        calculatorService.calculate(transactions);
    }

    @Test(expected = NullPointerException.class)
    public void calculate_transactionListWithNullElement_notOk() {
        FruitTransaction balanceTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, APPLE_QUANTITY);
        FruitTransaction supplyTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, BANANA_QUANTITY);
        List<FruitTransaction> transactions =
                Arrays.asList(balanceTransaction, null, supplyTransaction);
        calculatorService.calculate(transactions);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
