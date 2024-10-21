package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ShopService;
import strategy.BalanceOperation;
import strategy.OperationHandler;
import strategy.OperationStrategy;
import strategy.OperationStrategyImpl;
import strategy.PurchaseOperation;
import strategy.ReturnOperation;
import strategy.SupplyOperation;

class ShopServiceTest {
    private static Map<FruitTransaction.Operation, OperationHandler> handlers;
    private OperationStrategy operationStrategy;
    private ShopService shopService;
    private FruitTransaction balanceTransaction;
    private FruitTransaction purchaseTransaction;
    private FruitTransaction supplyTransaction;
    private FruitTransaction returnTransaction;

    @BeforeAll
    static void beforeAll() {
        handlers = Map.of(FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation());
    }

    @BeforeEach
    void setUp() {
        operationStrategy = new OperationStrategyImpl(handlers);
        shopService = new ShopServiceImpl(operationStrategy);
        balanceTransaction = new FruitTransaction("b", "grape", 12);
        purchaseTransaction = new FruitTransaction("p", "grape", 5);
        supplyTransaction = new FruitTransaction("s", "grape", 9);
        returnTransaction = new FruitTransaction("r", "grape", 3);
    }

    @Test
    void getHandler_balanceTransaction_ok() {
        OperationHandler expectedHandler = new BalanceOperation();
        OperationHandler actualHandler = shopService.getHandler(balanceTransaction);
        assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }

    @Test
    void getHandler_purchaseTransaction_ok() {
        OperationHandler expectedHandler = new PurchaseOperation();
        OperationHandler actualHandler = shopService.getHandler(purchaseTransaction);
        assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }

    @Test
    void getHandler_supplyOperation_ok() {
        OperationHandler expectedHandler = new SupplyOperation();
        OperationHandler actualHandler = shopService.getHandler(supplyTransaction);
        assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }

    @Test
    void getHandler_returnOperation_ok() {
        OperationHandler expectedHandler = new ReturnOperation();
        OperationHandler actualHandler = shopService.getHandler(returnTransaction);
        assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }
}
