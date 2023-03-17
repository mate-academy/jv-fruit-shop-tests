package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalculatorServiceImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int APPLE_QUANTITY = 50;
    private static final int BANANA_QUANTITY = 20;
    private static final int PURCHASE_QUANTITY = 10;
    private static final int RETURN_QUANTITY = 5;
    private static final int PURCHASE_INSUFFICIENT_QUANTITY = 60;

    private CalculatorService calculatorService;

    @BeforeEach
    void setUp() {
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
    void calculate_validTransactions_ok() {
        FruitTransaction balanceTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, APPLE_QUANTITY);
        FruitTransaction supplyTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, BANANA_QUANTITY);
        FruitTransaction purchaseTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        APPLE,
                        PURCHASE_QUANTITY);
        FruitTransaction returnTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, BANANA, RETURN_QUANTITY);
        List<FruitTransaction> transactions =
                Arrays.asList(balanceTransaction,
                        supplyTransaction,
                        purchaseTransaction,
                        returnTransaction);
        calculatorService.calculate(transactions);
        assertEquals(APPLE_QUANTITY - PURCHASE_QUANTITY, Storage.storage.get(APPLE));
        assertEquals(BANANA_QUANTITY + RETURN_QUANTITY, Storage.storage.get(BANANA));
    }

    @Test
    void calculate_purchaseWithInsufficientStock_noOk() {
        FruitTransaction balanceTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        APPLE,
                        APPLE_QUANTITY);
        FruitTransaction purchaseTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        APPLE,
                        PURCHASE_INSUFFICIENT_QUANTITY);
        List<FruitTransaction> transactions = Arrays.asList(balanceTransaction,
                        purchaseTransaction);
        assertThrows(RuntimeException.class, () -> calculatorService.calculate(transactions));
    }

    @Test
    void calculate_nullTransactionList_notOk() {
        List<FruitTransaction> transactions = null;
        assertThrows(RuntimeException.class, () -> calculatorService.calculate(transactions));
    }

    @Test
    void calculate_emptyTransactionList_notOk() {
        List<FruitTransaction> transactions = List.of();
        assertThrows(RuntimeException.class, () -> calculatorService.calculate(transactions));
    }

    @Test
    void calculate_transactionListWithNullElement_notOk() {
        FruitTransaction balanceTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, APPLE_QUANTITY);
        FruitTransaction supplyTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, BANANA_QUANTITY);
        List<FruitTransaction> transactions =
                Arrays.asList(balanceTransaction, null, supplyTransaction);
        assertThrows(NullPointerException.class, () -> calculatorService.calculate(transactions));
    }
}
