package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.operation.BalanceHandler;
import core.basesyntax.operation.OperationTypeStrategy;
import core.basesyntax.operation.OperationTypeStrategyImpl;
import core.basesyntax.operation.PurchaseHandler;
import core.basesyntax.operation.ReturnHandler;
import core.basesyntax.operation.ShopOperationHandler;
import core.basesyntax.operation.SupplyHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static List<TransactionDto> transactionDtoList;
    private static FruitShopService fruitShopService;
    private static Map<OperationType, ShopOperationHandler> shopOperationMap;
    private static OperationTypeStrategy operationTypeStrategy;
    private static TransactionDto transactionDto;

    private static ShopOperationHandler shopOperationHandler;

    @BeforeClass
    public static void setUp() {
        transactionDtoList = new ArrayList<>();
        shopOperationMap = new HashMap<>();
        shopOperationMap.put(OperationType.BALANCE, new BalanceHandler());
        shopOperationMap.put(OperationType.PURCHASE, new PurchaseHandler());
        shopOperationMap.put(OperationType.SUPPLY, new SupplyHandler());
        shopOperationMap.put(OperationType.RETURN, new ReturnHandler());
        operationTypeStrategy = new OperationTypeStrategyImpl(shopOperationMap);
        fruitShopService = new FruitShopServiceImpl(operationTypeStrategy);
    }

    @Test
    public void saveInformation_CheckValidData_Ok() {
        Map<Fruit, Integer> currentQuantity = new HashMap<>();
        Fruit banana = new Fruit("banana");
        currentQuantity.put(banana, 45);
        transactionDtoList = new ArrayList<>();
        transactionDtoList.add(new TransactionDto(OperationType.BALANCE, new Fruit("banana"), 50));
        transactionDtoList.add(new TransactionDto(OperationType.PURCHASE, new Fruit("banana"), 5));
        fruitShopService.saveInformation(transactionDtoList);
        assertEquals(currentQuantity.get(banana), FruitsStorage.fruitsStorage.get(banana));
    }

    @Test (expected = RuntimeException.class)
    public void saveInformation_InvalidOperationType_NotOk() {
        transactionDto = new TransactionDto(
                OperationType.getOperationType("f"),
                new Fruit("apple"),
                39);
        shopOperationHandler.getOperationResult(transactionDto);
    }

    @Test (expected = RuntimeException.class)
    public void saveInformation_InvalidAmount_NotOk() {
        transactionDto = new TransactionDto(
                OperationType.BALANCE,
                new Fruit("apple"),
                -39);
        shopOperationHandler.getOperationResult(transactionDto);
    }

    @Test
    public void report_CheckValidData_Ok() {
        transactionDtoList = new ArrayList<>();
        transactionDtoList.add(new TransactionDto(
                OperationType.BALANCE,
                new Fruit("apple"),
                50));
        fruitShopService.saveInformation(transactionDtoList);
        String actual = fruitShopService.report();
        String expected = "fruit,quantity".trim() + System.lineSeparator()
                + "apple,50".trim() + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        transactionDtoList.clear();
    }
}
