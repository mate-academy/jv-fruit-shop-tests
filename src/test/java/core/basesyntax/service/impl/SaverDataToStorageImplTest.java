package core.basesyntax.service.impl;

import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.ApplierFruitsToStorage;
import core.basesyntax.storage.DataBase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SaverDataToStorageImplTest {
    private static final Map<String, Integer> db = new HashMap<>();
    private static final OperationType operationType
            = core.basesyntax.service.impl.OperationType
            .getOperationType("s");
    private static final ApplierFruitsToStorage purchaseHandler
            = new PurchaseFruitHandlerImpl();
    private static FruitRecordDto fruitRecordDto;
    private static FruitRecordDto secondFruitRecordDto
            = new FruitRecordDto(OperationType.SUPPLY, "apple", 30);
    private static final AddHandlerImpl addHandler = new AddHandlerImpl();
    private static SaverDataToStorageImpl saverDataToStorage
            = new SaverDataToStorageImpl();
    private static int amount;
    private static List<FruitRecordDto> fruitDtos = new ArrayList<>();

    private static Map<OperationType, ApplierFruitsToStorage> operationStrategyMap
            = new HashMap<>();

    @BeforeEach
    void setUp() {
        amount = 50;
        fruitRecordDto
                = new FruitRecordDto(operationType, "apple", amount);
    }

    @Test
    void saveDataToStorageTest_Ok() {
        operationStrategyMap.put(OperationType.BALANCE, addHandler);
        operationStrategyMap.put(OperationType.RETURN, addHandler);
        operationStrategyMap.put(OperationType.SUPPLY, addHandler);
        operationStrategyMap.put(OperationType.PURCHASE, purchaseHandler);
        fruitDtos.add(fruitRecordDto);
        fruitDtos.add(secondFruitRecordDto);

        db.put(fruitRecordDto.getName(), fruitRecordDto.getAmount());
        db.put(secondFruitRecordDto.getName(),db.get(fruitRecordDto.getName())
                + secondFruitRecordDto.getAmount());
        saverDataToStorage.saveDataToStorage(fruitDtos, operationStrategyMap);
        Assertions.assertEquals(db, DataBase.getDataBase());
        DataBase.getDataBase().remove(fruitRecordDto.getName());
    }
}
