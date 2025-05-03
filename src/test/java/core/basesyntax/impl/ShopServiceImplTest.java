package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.OperationType;
import core.basesyntax.model.ShopOperation;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.StrategyOperationService;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.service.impl.StrategyOperationServiceImpl;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import exception.OperationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static final String APPLE = "apple";
    private static final int BALANCE_APPLES = 50;
    private static final int SUPPLY_APPLES = 50;
    private static final int PURCHASE_APPLES = 50;
    private static final int RETURN_APPLES = 50;
    private static final int EXPECTED_QUANTITY = 100;
    private static ShopService shopService;
    private static StrategyOperationService strategyOperationService;
    private static Map<OperationType, OperationHandler> operationHandlersMap;
    private static List<ShopOperation> shopOperations;
    private static ShopOperation balanceOperation;
    private static ShopOperation supplyOperation;
    private static ShopOperation purchaseOperation;
    private static ShopOperation returnOperation;

    @BeforeAll
    static void beforeAll() {
        operationHandlersMap = new HashMap<>();
        operationHandlersMap.put(OperationType.BALANCE, new BalanceOperation());
        operationHandlersMap.put(OperationType.SUPPLY, new SupplyOperation());
        operationHandlersMap.put(OperationType.PURCHASE, new PurchaseOperation());
        operationHandlersMap.put(OperationType.RETURN, new ReturnOperation());
        strategyOperationService = new StrategyOperationServiceImpl(operationHandlersMap);
        shopService = new ShopServiceImpl(strategyOperationService);

        shopOperations = new ArrayList<>();
        balanceOperation = new ShopOperation(OperationType.BALANCE, APPLE, BALANCE_APPLES);
        supplyOperation = new ShopOperation(OperationType.SUPPLY, APPLE, SUPPLY_APPLES);
        purchaseOperation = new ShopOperation(OperationType.PURCHASE, APPLE, PURCHASE_APPLES);
        returnOperation = new ShopOperation(OperationType.RETURN, APPLE, RETURN_APPLES);

        shopOperations.add(balanceOperation);
        shopOperations.add(supplyOperation);
        shopOperations.add(purchaseOperation);
        shopOperations.add(returnOperation);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitsStorage.clear();
    }

    @Test
    void process_useAllShopOperations_Ok() {
        shopService.process(shopOperations);
        assertEquals(EXPECTED_QUANTITY, Storage.fruitsStorage.get(APPLE));
    }

    @Test
    void process_useOnlySupplyShopOperation_NotOk() {
        ShopOperation supplyOperation = new ShopOperation(
                OperationType.SUPPLY, APPLE, SUPPLY_APPLES);
        List<ShopOperation> onlySupplyOperation = new ArrayList<>();
        onlySupplyOperation.add(supplyOperation);
        OperationException exception = assertThrows(OperationException.class,
                () -> shopService.process(onlySupplyOperation));
        String expected = "Operation is not correct, fruit doesn't exist: " + APPLE;
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void process_useOnlyPurchaseShopOperation_NotOk() {
        ShopOperation purchaseOperation = new ShopOperation(
                OperationType.PURCHASE, APPLE, PURCHASE_APPLES);
        List<ShopOperation> onlyPurchaseOperation = new ArrayList<>();
        onlyPurchaseOperation.add(purchaseOperation);
        OperationException exception = assertThrows(OperationException.class,
                () -> shopService.process(onlyPurchaseOperation));
        String expected = "Operation is not correct, fruit doesn't exist: " + APPLE;
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void process_useOnlyReturnShopOperation_NotOk() {
        ShopOperation returnShopOperation = new ShopOperation(
                OperationType.RETURN, APPLE, RETURN_APPLES);
        List<ShopOperation> onlyReturnOperation = new ArrayList<>();
        onlyReturnOperation.add(returnShopOperation);
        OperationException exception = assertThrows(OperationException.class,
                () -> shopService.process(onlyReturnOperation));
        String expected = "Operation is not correct, fruit doesn't exist: " + APPLE;
        assertEquals(expected, exception.getMessage());
    }
}
