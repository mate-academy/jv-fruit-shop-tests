package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoMapImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.FruitRecordStrategy;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.handler.AddAmountHandler;
import core.basesyntax.service.handler.RecordHandler;
import core.basesyntax.service.handler.SubtractAmountHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static FruitService fruitService;
    private static FruitDao fruitDao;
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final String ORANGE = "orange";

    @BeforeClass
    public static void setFruitService() {
        fruitDao = new FruitDaoMapImpl();
        RecordHandler addHandler = new AddAmountHandler(fruitDao);
        Map<FruitRecordDto.Type, RecordHandler> operationStrategies = new HashMap<>();
        operationStrategies.put(FruitRecordDto.Type.b, addHandler);
        operationStrategies.put(FruitRecordDto.Type.p, new SubtractAmountHandler(fruitDao));
        operationStrategies.put(FruitRecordDto.Type.s, addHandler);
        operationStrategies.put(FruitRecordDto.Type.r, addHandler);
        FruitRecordStrategy fruitRecordStrategy = new FruitRecordStrategyImpl(operationStrategies);
        fruitService = new FruitServiceImpl(fruitRecordStrategy, fruitDao);
    }

    @Before
    public void cleanDB() {
        Storage.storage.clear();
    }

    @Test
    public void saveData_isOk() {
        List<FruitRecordDto> inputData = List.of(
                new FruitRecordDto(FruitRecordDto.Type.b, BANANA, 20),
                new FruitRecordDto(FruitRecordDto.Type.b, APPLE, 100),
                new FruitRecordDto(FruitRecordDto.Type.s, BANANA, 100),
                new FruitRecordDto(FruitRecordDto.Type.p, APPLE, 50),
                new FruitRecordDto(FruitRecordDto.Type.r, BANANA, 50));
        fruitService.saveData(inputData);
        Optional<Fruit> banana = fruitDao.get(BANANA);
        Optional<Fruit> apple = fruitDao.get(APPLE);
        Optional<Fruit> orange = fruitDao.get(ORANGE);
        assertEquals(170, banana.get().getAmount());
        assertEquals(50, apple.get().getAmount());
        assertEquals(Optional.empty(), orange);
    }

    @Test
    public void getFruitReport_isOk() {
        List<FruitRecordDto> inputData = List.of(
                new FruitRecordDto(FruitRecordDto.Type.b, BANANA, 20),
                new FruitRecordDto(FruitRecordDto.Type.b, APPLE, 100),
                new FruitRecordDto(FruitRecordDto.Type.s, BANANA, 100),
                new FruitRecordDto(FruitRecordDto.Type.p, APPLE, 50),
                new FruitRecordDto(FruitRecordDto.Type.r, BANANA, 50));
        fruitService.saveData(inputData);
        String fruitReport = fruitService.getFruitReport();
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,170" + System.lineSeparator()
                + "apple,50";
        assertEquals(expected, fruitReport);
    }
}
