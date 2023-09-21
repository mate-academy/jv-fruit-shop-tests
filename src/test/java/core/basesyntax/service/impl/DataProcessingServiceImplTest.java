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
    void processing_successProcessedSupllyOperation_Ok() {
        Storage.STORAGE.put("banana", 50);
        Storage.STORAGE.put("apple", 20);
        String banana = "banana";
        String apple = "apple";
        int bananaSupplyAmount = 100;
        int appleSupplyAmount = 30;
        List<String[]> supplyItem = List.of(new String[]{"s", "banana",
                                    String.valueOf(bananaSupplyAmount)},
                                    new String[]{"s", "apple", String.valueOf(appleSupplyAmount)});
        processingService.processing(supplyItem);
        int bananaExpected = 50 + bananaSupplyAmount;
        int appleExpected = 20 + appleSupplyAmount;
        int bananaActual = Storage.STORAGE.get(banana);
        int appleActual = Storage.STORAGE.get(apple);
        assertEquals(bananaExpected, bananaActual);
        assertEquals(appleExpected, appleActual);
    }

    @Test
    void processing_successProcessedReturnOperation_Ok() {
        Storage.STORAGE.put("banana", 50);
        Storage.STORAGE.put("apple", 20);
        String banana = "banana";
        String apple = "apple";
        int bananaReturnAmount = 100;
        int appleReturnAmount = 30;
        List<String[]> supplyItem = List.of(new String[]{"r", "banana",
                    String.valueOf(bananaReturnAmount)},
                    new String[]{"r", "apple", String.valueOf(appleReturnAmount)});
        processingService.processing(supplyItem);
        int bananaExpected = 50 + bananaReturnAmount;
        int appleExpected = 20 + appleReturnAmount;
        int bananaActual = Storage.STORAGE.get(banana);
        int appleActual = Storage.STORAGE.get(apple);
        assertEquals(bananaExpected, bananaActual);
        assertEquals(appleExpected, appleActual);
    }

    @Test
    void processing_successProcessedPurchaseOperation_Ok() {
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

    @Test
    void processing_successProcessedBalanceOperation_Ok() {
        Storage.STORAGE.put("banana", 150);
        Storage.STORAGE.put("apple", 50);
        String banana = "banana";
        String apple = "apple";
        List<String[]> supplyItem = List.of(new String[]{"b", "banana", "150"},
                new String[]{"b", "apple", "50"});
        processingService.processing(supplyItem);
        int bananaExpected = 150;
        int appleExpected = 50;
        int bananaActual = Storage.STORAGE.get(banana);
        int appleActual = Storage.STORAGE.get(apple);
        assertEquals(bananaExpected, bananaActual);
        assertEquals(appleExpected, appleActual);
    }
}
