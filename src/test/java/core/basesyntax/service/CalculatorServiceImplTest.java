package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.CalculatorServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import core.basesyntax.strategy.impl.StrategyStorageImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorServiceImplTest {
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
        FruitTransaction balanceTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 50);
        FruitTransaction supplyTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 20);
        FruitTransaction purchaseTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 10);
        FruitTransaction returnTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 5);
        List<FruitTransaction> transactions = Arrays.asList(balanceTransaction, supplyTransaction, purchaseTransaction, returnTransaction);
        calculatorService.calculate(transactions);
        assertEquals(40, Storage.storage.get("apple"));
        assertEquals(25, Storage.storage.get("banana"));
    }

    @Test
    void calculate_purchaseWithInsufficientStock_noOk() {
        FruitTransaction balanceTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 50);
        FruitTransaction purchaseTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 60);
        List<FruitTransaction> transactions = Arrays.asList(balanceTransaction, purchaseTransaction);
        assertThrows(RuntimeException.class, () -> calculatorService.calculate(transactions));
    }

    @Test
    void calculate_nullTransactionList_throwsRuntimeException() {
        List<FruitTransaction> transactions = null;
        assertThrows(RuntimeException.class, () -> calculatorService.calculate(transactions));
    }

    @Test
    void calculate_emptyTransactionList_throwsRuntimeException() {
        List<FruitTransaction> transactions = Arrays.asList();
        assertThrows(RuntimeException.class, () -> calculatorService.calculate(transactions));
    }

    @Test
    void calculate_transactionListWithNullElement_throwsNullPointerException() {
        FruitTransaction balanceTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 50);
        FruitTransaction supplyTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 20);
        List<FruitTransaction> transactions = Arrays.asList(balanceTransaction, null, supplyTransaction);
        assertThrows(NullPointerException.class, () -> calculatorService.calculate(transactions));
    }
}