package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.strategy.FruitOperationHandler;
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
        fruitService = new FruitServiceImpl(strategyMap);
    }

    @Test
    public void save_EmptyListRecordDto_Ok() {
        java.util.List<FruitRecordDto> recordDto = new ArrayList<>();
        fruitService.saveThis(recordDto);
        actual = Storage.fruitsDataBase;
        expected = new HashMap<>();
        assertEquals(expected, actual);
    }

    @Test
    public void getReport_fromEmptyStorage_Ok() {
        List<FruitRecordDto> recordDto = new ArrayList<>();
        fruitService.saveThis(recordDto);
        String actualReport = fruitService.getReport();
        String expectedReport = "fruit, quantity";
        assertEquals(expectedReport, actualReport);
    }

    @After
    public void clear() {
        Storage.fruitsDataBase.entrySet().clear();
    }
}
