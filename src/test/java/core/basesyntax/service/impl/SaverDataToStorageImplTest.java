package core.basesyntax.service.impl;

import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.ApplierFruitsToStorage;
import core.basesyntax.storage.DataBase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SaverDataToStorageImplTest {
    private static final AddHandlerImpl addHandler = new AddHandlerImpl();
    private static final SaverDataToStorageImpl saverDataToStorage
            = new SaverDataToStorageImpl();
    private static final ApplierFruitsToStorage purchaseHandler
            = new PurchaseFruitHandlerImpl();
    private static Map<String, Integer> testDataBase;
    private static OperationType operationType;
    private static FruitRecordDto firstFruitRecordDto;
    private static FruitRecordDto secondFruitRecordDto;
    private static int firstFruitDtoAmount;
    private static int secondFruitDtoAmount;
    private static List<FruitRecordDto> fruitDtos;
    private static Map<OperationType, ApplierFruitsToStorage> operationStrategyMap;

    @BeforeEach
    void setUp() {
        testDataBase = new HashMap<>();
        operationType = OperationType.SUPPLY;
        firstFruitDtoAmount = 30;
        secondFruitDtoAmount = 50;
        firstFruitRecordDto = new FruitRecordDto(operationType, "apple", firstFruitDtoAmount);
        secondFruitRecordDto = new FruitRecordDto(OperationType
                .SUPPLY, "apple", secondFruitDtoAmount);
        fruitDtos = new ArrayList<>();
        operationStrategyMap
                = new HashMap<>();
    }

    @AfterEach
    void tearDown() {
        DataBase.getDataBase().remove(firstFruitRecordDto.getName());
    }

    @Test
    void saveDataToStorageTest_Ok() {
        operationStrategyMap.put(OperationType.BALANCE, addHandler);
        operationStrategyMap.put(OperationType.RETURN, addHandler);
        operationStrategyMap.put(OperationType.SUPPLY, addHandler);
        operationStrategyMap.put(OperationType.PURCHASE, purchaseHandler);
        fruitDtos.add(firstFruitRecordDto);
        fruitDtos.add(secondFruitRecordDto);
        testDataBase.put(firstFruitRecordDto.getName(), firstFruitRecordDto.getAmount());
        testDataBase.put(secondFruitRecordDto.getName(), testDataBase
                .get(secondFruitRecordDto.getName())
                + secondFruitRecordDto.getAmount());
        saverDataToStorage.saveDataToStorage(fruitDtos, operationStrategyMap);
        Assertions.assertEquals(testDataBase, DataBase.getDataBase());
    }
}
