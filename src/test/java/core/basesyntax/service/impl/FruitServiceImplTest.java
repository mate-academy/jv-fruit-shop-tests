package core.basesyntax.service.impl;

import core.basesyntax.database.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.FruitOperationHandler;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.Operation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static Map<Operation, FruitOperationHandler> operations;
    private static FruitService fruitService;
    private static List<FruitRecordDto> fruitRecordDtoList;

    @BeforeClass
    public static void beforeClass() {
        operations = new HashMap<>();
        operations.put(Operation.BALANCE, new AddOperation());
        operations.put(Operation.RETURN, new AddOperation());
        operations.put(Operation.SUPPLY, new AddOperation());
        operations.put(Operation.PURCHASE, new PurchaseOperation());
        fruitService = new FruitServiceImpl(operations);
        fruitRecordDtoList = new ArrayList<>();
    }

    @Test
    public void applyOperation_Ok() {
        Map<Fruit, Integer> currentQuantity = new HashMap<>();
        Fruit banana = new Fruit("banana");
        currentQuantity.put(banana, 155);
        fruitRecordDtoList.add(new FruitRecordDto(Operation.BALANCE, "banana", 50));
        fruitRecordDtoList.add(new FruitRecordDto(Operation.RETURN, "banana", 10));
        fruitRecordDtoList.add(new FruitRecordDto(Operation.PURCHASE, "banana", 5));
        fruitRecordDtoList.add(new FruitRecordDto(Operation.SUPPLY, "banana", 100));
        fruitService.applyOperation(fruitRecordDtoList);
        Assert.assertEquals(currentQuantity.get(banana), Storage.fruits.get(banana));
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.fruits.clear();
    }
}
