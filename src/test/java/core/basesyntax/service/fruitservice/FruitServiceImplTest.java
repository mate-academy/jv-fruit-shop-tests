package core.basesyntax.service.fruitservice;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.service.fileservice.FileReader;
import core.basesyntax.service.fileservice.FileReaderForCsvImpl;
import core.basesyntax.service.handlers.AddOperationStrategy;
import core.basesyntax.service.handlers.RecordHandler;
import core.basesyntax.service.handlers.RemoveOperationStrategy;
import core.basesyntax.service.parser.FruitRecordDtoParser;
import core.basesyntax.service.parser.FruitRecordDtoParserImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FruitServiceImplTest {
    private static final String fromFile = "src/test/resources/valid_instructions.csv";
    private static FruitService fruitService;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @BeforeClass
    public static void setUp() {
        FruitDao fruitDao = new FruitDaoImpl();
        FileReader fileReader = new FileReaderForCsvImpl();
        FruitRecordDtoParser parser = new FruitRecordDtoParserImpl();
        RecordHandler addOperation = new AddOperationStrategy(fruitDao);
        RecordHandler removeOperation = new RemoveOperationStrategy(fruitDao);

        Map<Operation, RecordHandler> fruitOperationHandler = new HashMap<>();
        fruitOperationHandler.put(Operation.getOperationByLetter("s"), addOperation);
        fruitOperationHandler.put(Operation.getOperationByLetter("r"), addOperation);
        fruitOperationHandler.put(Operation.getOperationByLetter("b"), addOperation);
        fruitOperationHandler.put(Operation.getOperationByLetter("p"), removeOperation);
        FruitRecordStrategy fruitRecordStrategy =
                new FruitRecordStrategyImpl(fruitOperationHandler);
        List<FruitRecordDto> dtos = parser.parse(fileReader.readAllLinesFromFile(fromFile));
        fruitService = new FruitServiceImpl(fruitRecordStrategy, fruitDao);
        fruitService.saveData(dtos);
    }

    @Test
    public void check_saveData_OK() {
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("banana"), 437);
        expected.put(new Fruit("apple"), 390);
        Assert.assertFalse(Storage.fruits.isEmpty());
        Assert.assertEquals(expected, Storage.fruits);
    }

    @Test
    public void check_createReport_OK() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,437" + System.lineSeparator()
                + "apple,390" + System.lineSeparator();
        String actual = fruitService.createReport();
        Assert.assertNotNull(actual);
        Assert.assertFalse(actual.isEmpty() || actual.isBlank());
        Assert.assertEquals(expected, actual);
    }
}
