package service.impl;

import dao.FruitDaoImpl;
import db.Storage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ShopService;
import service.operation.BalanceOperation;
import service.operation.OperationHandler;
import strategy.OperationStrategyImpl;
import util.TestConstants;

class ShopServiceImplTest {
    private static ShopService shopService;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperation(new FruitDaoImpl()));
        shopService = new ShopServiceImpl(new OperationStrategyImpl(operationHandlers));
    }

    @BeforeEach
    void setUp() {
        Storage.fruitStock.clear();
    }

    @Test
    void process_validTransaction_isOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, TestConstants.BANANA, 20);
        List<FruitTransaction> input = List.of(fruitTransaction);
        shopService.process(input);
        String expectedFruit = TestConstants.BANANA;
        int expectedQuantity = 20;
        int actualQuantity = Storage.fruitStock.get(expectedFruit);
        Assertions.assertTrue(Storage.fruitStock.containsKey(expectedFruit));
        Assertions.assertEquals(expectedQuantity, actualQuantity);
    }
}
