package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.FruitOperationHandler;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static FruitService fruitService;
    private static List<FruitRecordDto> recordDto;
    private static Map<Operation, FruitOperationHandler> operationStrategyMap;
    private static Map<Fruit, Integer> expected;
    private static Map<Fruit, Integer> actual;

    @BeforeClass
    public static void initializeObject() {
        Map<Operation, FruitOperationHandler> strategyMap = new HashMap<>();
        recordDto = new ArrayList<>();
        fruitService = new FruitServiceImpl(strategyMap);
        operationStrategyMap = new HashMap<>();
        operationStrategyMap.put(Operation.BALANCE, new BalanceOperation());
        operationStrategyMap.put(Operation.SUPPLY, new SupplyOperation());
        operationStrategyMap.put(Operation.PURCHASE, new PurchaseOperation());
        operationStrategyMap.put(Operation.RETURN, new ReturnOperation());
    }

    @Test
    public void save_EmptyListRecordDto_Ok() {
        fruitService.save(recordDto, operationStrategyMap);
        actual = Storage.fruitsDataBase;
        expected = new HashMap<>();
        assertEquals(expected, actual);
    }

    @Test
    public void save_validListRecordDto_Ok() {
        recordDto.add(new FruitRecordDto(Operation.BALANCE, "banana", 5));
        fruitService.save(recordDto, operationStrategyMap);
        actual = Storage.fruitsDataBase;
        expected = new HashMap<>();
        expected.put(new Fruit("banana"), 5);
        assertEquals(expected, actual);
    }

    @Test
    public void getReport_fromEmptyStorage_Ok() {
        fruitService.save(recordDto, operationStrategyMap);
        String actualReport = fruitService.getReport();
        String expectedReport = "fruit, quantity";
        assertEquals(expectedReport, actualReport);
    }

    @Test
    public void getReport_fromValidRecordDto_Ok() {
        recordDto.add(new FruitRecordDto(Operation.BALANCE, "banana", 5));
        fruitService.save(recordDto, operationStrategyMap);
        String actualReport = fruitService.getReport();
        String expectedReport = "fruit, quantity" + System.lineSeparator()
                + "banana,5";
        assertEquals(expectedReport, actualReport);
    }

    @After
    public void clear() {
        recordDto.clear();
        Storage.fruitsDataBase.clear();
    }
}
