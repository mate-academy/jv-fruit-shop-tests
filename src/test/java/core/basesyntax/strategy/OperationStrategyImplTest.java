package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static OperationStrategy operationStrategy;
    private static OperationHandler balanceOperationHandler;
    private static OperationHandler supplyOperationHandler;
    private static OperationHandler purchaseOperationHandler;
    private static OperationHandler returnOperationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandlerMap = new HashMap<>();
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        balanceOperationHandler = new BalanceOperationHandler();
        supplyOperationHandler = new SupplyOperationHandler();
        purchaseOperationHandler = new PurchaseOperationHandler();
        returnOperationHandler = new ReturnOperationHandler();
    }

    @AfterEach
    void tearDown() {
        operationHandlerMap.clear();
    }

    @Test
    void get_checkOnBalanceOperationHandler_Ok() {
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, balanceOperationHandler);
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        assertEquals(balanceOperationHandler,actual);
    }

    @Test
    void get_checkOnSupplyOperationHandler_Ok() {
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, supplyOperationHandler);
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        assertEquals(supplyOperationHandler,actual);
    }

    @Test
    void get_checkOnPurchaseOperationHandler_Ok() {
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, purchaseOperationHandler);
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        assertEquals(purchaseOperationHandler,actual);
    }

    @Test
    void get_checkOnReturnOperationHandler_Ok() {
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, returnOperationHandler);
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.RETURN);
        assertEquals(returnOperationHandler,actual);
    }
}
