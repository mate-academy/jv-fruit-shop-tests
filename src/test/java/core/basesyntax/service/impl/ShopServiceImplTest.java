package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.operation.BalanceOperation;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PurchaseOperation;
import core.basesyntax.strategy.operation.ReturnOperation;
import core.basesyntax.strategy.operation.SupplyOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlers;
    private static OperationStrategy operationStrategy;
    private static ShopService shopService;
    private static List<FruitTransaction> fruitTransactionList;

    @BeforeAll
    static void beforeAll() {
        Storage.fruits.clear();
        operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
        fruitTransactionList = new ArrayList<>();
    }

    @BeforeEach
    void setUp() {
        fruitTransactionList.clear();
        fruitTransactionList.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 20));
        fruitTransactionList.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100));
        fruitTransactionList.add(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 100));
        fruitTransactionList.add(new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 10));
        fruitTransactionList.add(new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 10));
        fruitTransactionList.add(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 50));
    }

    @Test
    void process_ValidFruitTransactionList_Ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 160);
        expected.put("apple", 110);
        shopService.process(fruitTransactionList);
        assertEquals(expected, Storage.fruits,
                "Test process_ValidFruitTransactionList_Ok failed");

    }
}
