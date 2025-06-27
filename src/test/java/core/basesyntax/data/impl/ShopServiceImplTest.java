package core.basesyntax.data.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.data.db.Storage;
import core.basesyntax.data.exeption.OperationException;
import core.basesyntax.data.model.FruitTransaction;
import core.basesyntax.data.model.OperationType;
import core.basesyntax.data.servise.ShopService;
import core.basesyntax.data.servise.StrategyOperationService;
import core.basesyntax.data.servise.ipl.ShopServiceImpl;
import core.basesyntax.data.servise.ipl.StrategyOperationServiceImpl;
import core.basesyntax.data.strategy.BalanceOperationHandler;
import core.basesyntax.data.strategy.OperationHandler;
import core.basesyntax.data.strategy.PurchaseOperationHandler;
import core.basesyntax.data.strategy.ReturnOperationHandler;
import core.basesyntax.data.strategy.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private static final String APPLE = "apple";
    private static final String ORANGE = "orange";
    private static final int BALANCE_APPLES = 50;
    private static final int SUPPLY_APPLES = 50;
    private static final int PURCHASE_APPLES = 30;
    private static final int RETURN_APPLES = 20;
    private static final int EXPECTED_QUANTITY_AFTER_ALL = 90;
    private static ShopService shopService;
    private static StrategyOperationService strategyOperationService;
    private static Map<OperationType, OperationHandler> operationHandlersMap;
    private static List<FruitTransaction> shopOperations;
    private static FruitTransaction balanceOperation;
    private static FruitTransaction supplyOperation;
    private static FruitTransaction purchaseOperation;
    private static FruitTransaction returnOperation;

    @BeforeAll
    static void beforeAll() {
        operationHandlersMap = new HashMap<>();
        operationHandlersMap.put(OperationType.BALANCE, new BalanceOperationHandler());
        operationHandlersMap.put(OperationType.SUPPLY, new SupplyOperationHandler());
        operationHandlersMap.put(OperationType.PURCHASE, new PurchaseOperationHandler());
        operationHandlersMap.put(OperationType.RETURN, new ReturnOperationHandler());
        strategyOperationService = new StrategyOperationServiceImpl(operationHandlersMap);
        shopService = new ShopServiceImpl(strategyOperationService);

        shopOperations = new ArrayList<>();
        balanceOperation = new FruitTransaction(OperationType.BALANCE, APPLE, BALANCE_APPLES);
        supplyOperation = new FruitTransaction(OperationType.SUPPLY, APPLE, SUPPLY_APPLES);
        purchaseOperation = new FruitTransaction(OperationType.PURCHASE, APPLE, PURCHASE_APPLES);
        returnOperation = new FruitTransaction(OperationType.RETURN, APPLE, RETURN_APPLES);

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
        assertEquals(EXPECTED_QUANTITY_AFTER_ALL, Storage.fruitsStorage.get(APPLE));
    }

    @Test
    void process_useOnlySupplyShopOperation_NotOk_fruitDoesNotExist() {
        FruitTransaction supplyOperation = new FruitTransaction(
                OperationType.SUPPLY, APPLE, SUPPLY_APPLES);
        List<FruitTransaction> onlySupplyOperation = Collections.singletonList(supplyOperation);
        OperationException exception = assertThrows(OperationException.class,
                () -> shopService.process(onlySupplyOperation));
        String expected = "Operation is not correct, fruit doesn't exist: " + APPLE;
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void process_useOnlyPurchaseShopOperation_NotOk_fruitDoesNotExist() {
        FruitTransaction purchaseOperation = new FruitTransaction(
                OperationType.PURCHASE, APPLE, PURCHASE_APPLES);
        List<FruitTransaction> onlyPurchaseOperation = Collections.singletonList(purchaseOperation);
        OperationException exception = assertThrows(OperationException.class,
                () -> shopService.process(onlyPurchaseOperation));
        String expected = "Operation is not correct, fruit doesn't exist: " + APPLE;
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void processTransaction_emptyList_Ok() {
        List<FruitTransaction> transactions = Collections.emptyList();
        shopService.process(transactions);
        assertEquals(0, Storage.fruitsStorage.size());
    }

    @Test
    void process_useOnlyReturnShopOperation_NotOk() {
        FruitTransaction returnShopOperation = new FruitTransaction(
                OperationType.RETURN, APPLE, RETURN_APPLES);
        List<FruitTransaction> onlyReturnOperation = Collections.singletonList(returnShopOperation);
        OperationException exception = assertThrows(OperationException.class,
                () -> shopService.process(onlyReturnOperation));
        String expected = "Operation is not correct, fruit doesn't exist: " + APPLE;
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void process_purchaseMoreThanAvailable_NotOk() {
        Storage.fruitsStorage.put(APPLE, 10);
        FruitTransaction purchaseMore = new FruitTransaction(OperationType.PURCHASE,APPLE, 20);
        List<FruitTransaction> operations = Collections.singletonList(purchaseMore);
        OperationException exception = assertThrows(OperationException.class,
                () -> shopService.process(operations));
        String expected = "Operation is impossible, not enough quantity of: " + APPLE;
        assertEquals(expected, exception.getMessage());
        assertEquals(10, Storage.fruitsStorage.get(APPLE));
    }

}
