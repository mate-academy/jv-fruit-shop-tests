package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.bd.Storage;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.service.type.service.BalanceHandler;
import core.basesyntax.service.type.service.OperationHandler;
import core.basesyntax.service.type.service.PurchaseHandler;
import core.basesyntax.service.type.service.ReturnHandler;
import core.basesyntax.service.type.service.SupplyHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static OperationStrategy operationStrategy;
    private static FruitShopServiceImpl shopService;
    private static FruitRecordDto apple;
    private static FruitRecordDto banana;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Map<FruitRecordDto.Activities, OperationHandler> mapTypeHandler = new HashMap<>();
        mapTypeHandler.put(FruitRecordDto.Activities.BALANCE, new BalanceHandler());
        mapTypeHandler.put(FruitRecordDto.Activities.PURCHASE, new PurchaseHandler());
        mapTypeHandler.put(FruitRecordDto.Activities.RETURN, new ReturnHandler());
        mapTypeHandler.put(FruitRecordDto.Activities.SUPPLY, new SupplyHandler());
        operationStrategy = new OperationStrategyImpl(mapTypeHandler);
        shopService = new FruitShopServiceImpl();
        apple = new FruitRecordDto();
        apple.setType(FruitRecordDto.Activities.BALANCE);
        apple.setFruit("apple");
        apple.setAmount(300);
        banana = new FruitRecordDto();
        banana.setType(FruitRecordDto.Activities.BALANCE);
        banana.setFruit("banana");
        banana.setAmount(100);
        Storage.records.add(apple);
        Storage.records.add(banana);
        shopService.transfer(operationStrategy);
    }

    @Test
    public void transfer_correctTransfer_Ok() {
        Storage.records.add(apple);
        Storage.records.add(banana);
        shopService.transfer(operationStrategy);
        int expected1 = banana.getAmount();
        int actual1 = Storage.fruitQuantity.get("banana");
        assertEquals(expected1, actual1);
        int expected2 = apple.getAmount();
        int actual2 = Storage.fruitQuantity.get("apple");
        assertEquals(expected2, actual2);
    }

    @Test
    public void transfer_incorrectTransfer_Ok() {
        int expected1 = apple.getAmount();
        int actual1 = Storage.fruitQuantity.get("banana");
        assertNotEquals(expected1, actual1);
        int expected2 = banana.getAmount();
        int actual2 = Storage.fruitQuantity.get("apple");
        assertNotEquals(expected2, actual2);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.records.clear();
        Storage.fruitQuantity.clear();
    }
}
