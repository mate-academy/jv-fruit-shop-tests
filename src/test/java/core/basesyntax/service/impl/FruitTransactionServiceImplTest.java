package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.FruitTransactionException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.service.transactions.BalanceOperationHandler;
import core.basesyntax.service.transactions.OperationHandler;
import core.basesyntax.service.transactions.PurchaseOperationHandler;
import core.basesyntax.service.transactions.ReturnOperationHandler;
import core.basesyntax.service.transactions.SupplyOperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitTransactionServiceImplTest {
    private static FruitTransactionService transactionService;
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void beforeAll() {
        operationStrategy = new OperationStrategyImpl(createOperationMap());
        transactionService = new FruitTransactionServiceImpl(operationStrategy);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitTransactions.clear();
    }

    @Test
    void test_addOrUpdate_ok() {
        Map<String, Integer> fruitMap =
                transactionService.addOrUpdate(FruitTransactionCreator.createValidTransactions());
        assertEquals(1,fruitMap.size(),"size should be 1, added not properly");
        assertEquals(1,Storage.fruitTransactions.size(),"size should be 1, added not properly");
        assertEquals(19,fruitMap.get("banana"));
    }

    @Test
    void test_addOrUpdate_negativeBalancePurchase_notOk() {
        FruitTransaction firstFruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,"banana",100);
        FruitTransaction secondFruitTransaction =
                new FruitTransaction(
                        FruitTransaction.Operation.PURCHASE,"banana",101);
        List<FruitTransaction> fruitTransactions =
                List.of(firstFruitTransaction,secondFruitTransaction);
        assertThrows(FruitTransactionException.class,
                () -> transactionService.addOrUpdate(fruitTransactions));
    }

    private static Map<FruitTransaction.Operation, OperationHandler> createOperationMap() {
        Map<FruitTransaction.Operation, OperationHandler> strategyMap = new HashMap<>();
        strategyMap.put(FruitTransaction.Operation.BALANCE,new BalanceOperationHandler());
        strategyMap.put(FruitTransaction.Operation.PURCHASE,new PurchaseOperationHandler());
        strategyMap.put(FruitTransaction.Operation.SUPPLY,new SupplyOperationHandler());
        strategyMap.put(FruitTransaction.Operation.RETURN,new ReturnOperationHandler());
        return strategyMap;
    }
}
