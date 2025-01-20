package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.CalculationService;
import core.basesyntax.strategy.TransactionStrategy;
import core.basesyntax.strategy.TransactionStrategyImpl;
import core.basesyntax.strategy.transaction.BalanceTransactionHandlerImpl;
import core.basesyntax.strategy.transaction.PurchaseTransactionHandlerImpl;
import core.basesyntax.strategy.transaction.ReturnTransactionHandlerImpl;
import core.basesyntax.strategy.transaction.SupplyTransactionHandlerImpl;
import core.basesyntax.strategy.transaction.TransactionHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CalculationServiceImplTest {
    private static CalculationService calculationService;
    private static Map<String, Integer> fruitsMap;
    private static List<FruitTransaction> fruitTransactions;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, TransactionHandler> transactionHandlerMap = new HashMap<>();
        transactionHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceTransactionHandlerImpl());
        transactionHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnTransactionHandlerImpl());
        transactionHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyTransactionHandlerImpl());
        transactionHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseTransactionHandlerImpl());
        fruitTransactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));
        TransactionStrategy transactionStrategy =
                new TransactionStrategyImpl(transactionHandlerMap);
        calculationService = new CalculationServiceImpl(transactionStrategy);
        fruitsMap = new HashMap<>();
    }

    @Test
    void calculate_validData_ok() {
        calculationService.calculate(fruitTransactions);
        fruitsMap.put("banana", 57);
        fruitsMap.put("apple", 30);
        assertEquals(fruitsMap, Storage.fruitsMap);
    }

    @Test
    void calculate_nullData_notOk() {
        assertThrows(RuntimeException.class, () -> calculationService.calculate(null));
    }
}
