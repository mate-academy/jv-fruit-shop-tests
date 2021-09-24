package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitOperationDto;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ShopsService;
import core.basesyntax.service.operationstrategy.BalanceOperationHandler;
import core.basesyntax.service.operationstrategy.OperationHandler;
import core.basesyntax.service.operationstrategy.PurchaseOperationHandler;
import core.basesyntax.service.operationstrategy.ReturnOperationHandler;
import core.basesyntax.service.operationstrategy.SupplyOperationHandler;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ShopsServiceImplTest {
    public static final FruitOperationDto BALANCE_OPERATION_APPLE
            = new FruitOperationDto(FruitOperationDto.Type.BALANCE, "apple", 10);
    public static final FruitOperationDto CHANGE_BALANCE_APPLE
            = new FruitOperationDto(FruitOperationDto.Type.BALANCE, "apple", 20);
    public static final FruitOperationDto PURCHASE_OPERATION_APPLE
            = new FruitOperationDto(FruitOperationDto.Type.PURCHASE, "apple", 5);
    public static final FruitOperationDto SUPPLY_OPERATION_APPLE
            = new FruitOperationDto(FruitOperationDto.Type.SUPPLY, "apple", 5);
    public static final FruitOperationDto RETURN_OPERATION_APPLE
            = new FruitOperationDto(FruitOperationDto.Type.RETURN, "apple", 5);
    private static ShopsService shopsService;
    private static Map<String, Integer> excepted;
    private static List<FruitOperationDto> dtos;
    private static Map<String, Integer> actual;

    @BeforeClass
    public static void initialize() {
        Map<FruitOperationDto.Type, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitOperationDto.Type.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(FruitOperationDto.Type.PURCHASE, new PurchaseOperationHandler());
        operationHandlerMap.put(FruitOperationDto.Type.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(FruitOperationDto.Type.RETURN, new ReturnOperationHandler());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        shopsService = new ShopsServiceImpl(operationStrategy);
        excepted = new HashMap<>();
        dtos = new LinkedList<>();
        actual = Storage.fruitStorage;
    }

    @Test
    public void shopsServiceImpl_getNormalData_Ok() {
        excepted.put("apple", 10);
        dtos.add(BALANCE_OPERATION_APPLE);
        shopsService.updateStorage(dtos);
        Map<String, Integer> actual = Storage.fruitStorage;
        assertEquals(excepted, actual);
    }

    @Test
    public void shopsServiceImpl_getTwoBalance_Ok() {
        excepted.put("apple", 20);
        dtos.add(BALANCE_OPERATION_APPLE);
        dtos.add(CHANGE_BALANCE_APPLE);
        shopsService.updateStorage(dtos);
        assertEquals(excepted, actual);
    }

    @Test
    public void shopsServiceImpl_getPurchaseOperation_Ok() {
        excepted.put("apple", 5);
        dtos.add(BALANCE_OPERATION_APPLE);
        dtos.add(PURCHASE_OPERATION_APPLE);
        shopsService.updateStorage(dtos);
        assertEquals(excepted, actual);
    }

    @Test
    public void shopsServiceImpl_getSupplyOperation_Ok() {
        excepted.put("apple", 15);
        dtos.add(BALANCE_OPERATION_APPLE);
        dtos.add(SUPPLY_OPERATION_APPLE);
        shopsService.updateStorage(dtos);
        assertEquals(excepted, actual);
    }

    @Test
    public void shopsServiceImpl_getReturnOperation_Ok() {
        excepted.put("apple", 15);
        dtos.add(BALANCE_OPERATION_APPLE);
        dtos.add(RETURN_OPERATION_APPLE);
        shopsService.updateStorage(dtos);
        assertEquals(excepted, actual);
    }
}
