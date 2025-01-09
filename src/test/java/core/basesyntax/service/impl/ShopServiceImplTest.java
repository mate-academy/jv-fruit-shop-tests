package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.ResultData;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static ShopService shopService;
    private static List<FruitTransaction> inputTransactions = new ArrayList<>();

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);

        shopService = new ShopServiceImpl(operationStrategy);
    }

    @AfterEach
    void tearDown() {
        inputTransactions.clear();
    }

    @Test
    void processTest_Ok() {
        inputTransactions.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "banana",
                20
        ));
        inputTransactions.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "apple",
                100
        ));
        inputTransactions.add(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                "banana",
                100
        ));
        inputTransactions.add(new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                "banana",
                13
        ));
        inputTransactions.add(new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                "apple",
                10
        ));

        List<ResultData> expectedResult = new ArrayList<>();
        expectedResult.add(new ResultData("banana", 107));
        expectedResult.add(new ResultData("apple",110));

        List<ResultData> actualResult = shopService.process(inputTransactions);

        Assertions.assertEquals(expectedResult, actualResult);
    }
}
