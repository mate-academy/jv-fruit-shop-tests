package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.CalculationService;
import core.basesyntax.strategy.TransactionStrategy;
import core.basesyntax.strategy.TransactionStrategyImpl;
import core.basesyntax.strategy.transaction.BalanceTransactionHandler;
import core.basesyntax.strategy.transaction.PurchaseTransactionHandler;
import core.basesyntax.strategy.transaction.ReturnTransactionHandler;
import core.basesyntax.strategy.transaction.SupplyTransactionHandler;
import core.basesyntax.strategy.transaction.TransactionHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CalculationServiceImplTest {
    private static CalculationService calculationService;
    private static Map<String, Integer> fruitMap;
    private static List<FruitTransaction> fruitTransactions;
    private static Map<FruitTransaction.Operation, TransactionHandler> transactionHandlerMap;

    @BeforeAll
    static void beforeAll() {
        transactionHandlerMap = new HashMap<>();
        transactionHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceTransactionHandler());
        transactionHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnTransactionHandler());
        transactionHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyTransactionHandler());
        transactionHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseTransactionHandler());
        fruitTransactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));
        TransactionStrategy transactionStrategy =
                new TransactionStrategyImpl(transactionHandlerMap);
        calculationService = new CalculationServiceImpl(transactionStrategy);
        fruitMap = new HashMap<>();
    }

    @Test
    void calculate_validData_ok() {
        calculationService.calculate(fruitTransactions);
        fruitMap.put("banana", 57);
        fruitMap.put("apple", 30);
        assertEquals(fruitMap, Storage.fruitMap);
    }

    @Test
    void calculate_nullData_notOk() {
        assertThrows(RuntimeException.class, () -> calculationService.calculate(null));
    }
}
