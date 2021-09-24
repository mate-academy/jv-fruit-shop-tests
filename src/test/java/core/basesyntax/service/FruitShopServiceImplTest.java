package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.bd.Storage;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.service.type.service.*;
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
        int expectedBanana = banana.getAmount();
        int actualBanana = Storage.fruitQuantity.get("banana");
        assertEquals(expectedBanana, actualBanana);
        int expectedApple = apple.getAmount();
        int actualApple = Storage.fruitQuantity.get("apple");
        assertEquals(expectedApple, actualApple);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.records.clear();
        Storage.fruitQuantity.clear();
    }
}
