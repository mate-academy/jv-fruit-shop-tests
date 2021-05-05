package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.strategy.AddBalanceOperation;
import core.basesyntax.strategy.FruitOperationHandler;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FruitServiceImplTest {
    private static FruitService fruitService;
    private static Map<Fruit, Integer> expected;
    private static Map<Fruit, Integer> actual;

    @Before
    public void setUp() {
        Map<Operation, FruitOperationHandler> strategyMap = new HashMap<>();
        strategyMap.put(Operation.BALANCE, new AddBalanceOperation());
        strategyMap.put(Operation.SUPPLY, new SupplyOperation());
        strategyMap.put(Operation.PURCHASE, new PurchaseOperation());
        strategyMap.put(Operation.RETURN, new ReturnOperation());

        fruitService = new FruitServiceImpl(strategyMap);
    }

    @Test
    public void save_ListRecordDtos_Ok() {
        List<FruitRecordDto> recordDtos = List.of(
                new FruitRecordDto(Operation.BALANCE, "banana", 40),
                new FruitRecordDto(Operation.BALANCE, "apple", 100),
                new FruitRecordDto(Operation.SUPPLY, "banana", 100),
                new FruitRecordDto(Operation.PURCHASE, "banana", 13),
                new FruitRecordDto(Operation.RETURN, "apple", 10),
                new FruitRecordDto(Operation.PURCHASE, "apple", 20),
                new FruitRecordDto(Operation.PURCHASE, "banana", 5),
                new FruitRecordDto(Operation.SUPPLY, "banana", 50));
        fruitService.save(recordDtos);
        actual = Storage.fruitsDataBase;
        expected = Map.of(new Fruit("banana"), 172, new Fruit("apple"), 90);
        assertEquals(expected, actual);
    }

    @Test
    public void save_EmptyListRecordDtos_Ok() {
        List<FruitRecordDto> recordDtos = new ArrayList<>();
        fruitService.save(recordDtos);
        actual = Storage.fruitsDataBase;
        expected = new HashMap<>();
        assertEquals(expected, actual);
    }

    @Test
    public void getReport_Ok() {
        List<FruitRecordDto> recordDtos = List.of(
                new FruitRecordDto(Operation.BALANCE, "banana", 40),
                new FruitRecordDto(Operation.BALANCE, "apple", 100),
                new FruitRecordDto(Operation.SUPPLY, "banana", 100),
                new FruitRecordDto(Operation.PURCHASE, "banana", 13),
                new FruitRecordDto(Operation.RETURN, "apple", 10),
                new FruitRecordDto(Operation.PURCHASE, "apple", 20),
                new FruitRecordDto(Operation.PURCHASE, "banana", 5),
                new FruitRecordDto(Operation.SUPPLY, "banana", 50));
        fruitService.save(recordDtos);
        String actualReport = fruitService.getReport();
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,172" + System.lineSeparator()
                + "apple,90";
        assertEquals(expectedReport, actualReport);
    }

    @Test
    public void getReport_fromEmptyStorage_Ok() {
        List<FruitRecordDto> recordDtos = new ArrayList<>();
        fruitService.save(recordDtos);
        String actualReport = fruitService.getReport();
        String expectedReport = "fruit,quantity";
        assertEquals(expectedReport, actualReport);
    }

    @After
    public void tearDown() {
        Storage.fruitsDataBase.entrySet().clear();
    }
}
