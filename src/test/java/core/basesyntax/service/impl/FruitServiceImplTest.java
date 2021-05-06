package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.FruitOperationHandler;
import core.basesyntax.service.FruitService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class FruitServiceImplTest {
    private static FruitService fruitService;
    private final List<FruitRecordDto> recordDtos = List.of(
            new FruitRecordDto(Operation.BALANCE, "banana", 10),
                new FruitRecordDto(Operation.SUPPLY, "banana", 10),
                new FruitRecordDto(Operation.RETURN, "banana", 10),
                new FruitRecordDto(Operation.PURCHASE, "banana", 20));

    @Before
    public void clearDbBeforeStart() {
        Storage.fruits.clear();
    }

    @Before
    public void setUp() {
        Map<Operation, FruitOperationHandler> operationStrategy = new HashMap<>();
        operationStrategy.put(Operation.BALANCE, new AddOperation());
        operationStrategy.put(Operation.PURCHASE, new PurchaseOperation());
        operationStrategy.put(Operation.RETURN, new AddOperation());
        operationStrategy.put(Operation.SUPPLY, new AddOperation());
        fruitService = new FruitServiceImpl(operationStrategy);
    }

    @Test
    public void addToStorage_Data_Ok() {
        fruitService.addToStorage(recordDtos);
        Map<Fruit, Integer> actual = Storage.fruits;
        Map<Fruit, Integer> expected = Map.of(new Fruit("banana"), 10);
        assertEquals(expected, actual);
    }

    @Test
    public void addToStorage_EmptyList_Ok() {
        List<FruitRecordDto> fruitRecordDtos = new ArrayList<>();
        fruitService.addToStorage(fruitRecordDtos);

        Map<Fruit, Integer> actual = Storage.fruits;
        Map<Fruit, Integer> expected = new HashMap<>();
        assertEquals(expected, actual);
    }

    @Test
    public void getReport_Ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,10" + System.lineSeparator();
        fruitService.addToStorage(recordDtos);
        String actual = fruitService.getReport();
        assertEquals(expected, actual);
    }
}
