package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.OperationType;
import core.basesyntax.service.DataProcessingService;
import core.basesyntax.service.FruitService;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationsHandler;
import core.basesyntax.strategy.impl.BalanceOperationsHandler;
import core.basesyntax.strategy.impl.PurchaseOperationsHandler;
import core.basesyntax.strategy.impl.ReturnOperationsHandler;
import core.basesyntax.strategy.impl.SupplyOperationsHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataProcessingServiceImplTest {
    private static DataProcessingService processingService;

    @BeforeAll
    static void beforeAll() {
        Map<OperationType, OperationsHandler> operationStrategies = new HashMap<>();
        operationStrategies.put(OperationType.BALANCE, new BalanceOperationsHandler());
        operationStrategies.put(OperationType.SUPPLY, new SupplyOperationsHandler());
        operationStrategies.put(OperationType.PURCHASE, new PurchaseOperationsHandler());
        operationStrategies.put(OperationType.RETURN, new ReturnOperationsHandler());
        FruitService fruitService = new FruitServiceImpl(operationStrategies);
        processingService = new DataProcessingServiceImpl(fruitService, 0, 1, 2);
    }

    @AfterEach
    void afterEach() {
        Storage.STORAGE.clear();
    }

    @Test
    void processing_successProcessedOperation_Ok() {
        Storage.STORAGE.put("banana", 150);
        Storage.STORAGE.put("apple", 50);
        String banana = "banana";
        String apple = "apple";
        int bananaPurchaseAmount = 100;
        int applePurchaseAmount = 30;
        List<String[]> supplyItem = List.of(new String[]{"p", "banana",
                String.valueOf(bananaPurchaseAmount)},
                new String[]{"p", "apple", String.valueOf(applePurchaseAmount)});
        processingService.processing(supplyItem);
        int bananaExpected = 150 - bananaPurchaseAmount;
        int appleExpected = 50 - applePurchaseAmount;
        int bananaActual = Storage.STORAGE.get(banana);
        int appleActual = Storage.STORAGE.get(apple);
        assertEquals(bananaExpected, bananaActual);
        assertEquals(appleExpected, appleActual);
    }
}
