package fruitshop.service.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import fruitshop.db.Storage;
import fruitshop.service.OperationStrategy;
import fruitshop.service.ReaderService;
import fruitshop.service.WriterService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FruitServiceImplTest {
    private static final String CORRECT_PATH_FOR_INPUT = "src/test/resources/input.txt";
    private static final String INCORRECT_PATH_FOR_INPUT = "test/resources/input.txt";
    private static final String CORRECT_PATH_FOR_OUTPUT = "src/test/resources/output.txt";
    private static final String INCORRECT_PATH_FOR_OUTPUT = "";

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private ReaderService readerService = new ReaderServiceImpl();
    private WriterService writerService = new WriterServiceImpl();
    private OperationStrategy operationStrategy = new OperationStrategyImpl();
    private FruitServiceImpl fruitService = new FruitServiceImpl(readerService, writerService,
            operationStrategy);

    @Test
    public void putStrategies_ok() {
        fruitService.putStrategies();
        assertEquals(4, OperationStrategyImpl.operationHandlerMap.size());
    }

    @Test
    public void parseDataFromList_ok() {
        List<String> fruits = new ArrayList<>();
        fruits.add("type,fruit,quantity");
        fruits.add("b,banana,20");
        fruits.add("b,apple,100");
        fruits.add("s,banana,100");
        fruits.add("p,banana,13");
        fruits.add("r,apple,10");
        fruits.add("p,apple,20");
        fruits.add("p,banana,5");
        fruits.add("s,banana,50");
        fruitService.putStrategies();
        fruitService.parseDataFromList(fruits);
        Map<String, Integer> fruitMap = new HashMap<>();
        fruitMap.put("banana", 152);
        fruitMap.put("apple", 90);
        assertEquals(2, Storage.fruitList.size());
        assertEquals(fruitMap, Storage.fruitList);
    }

    @Test
    public void generateReport_ok() {
        Storage.fruitList.put("banana", 152);
        Storage.fruitList.put("apple", 90);
        byte[] expected = {102, 114, 117, 105, 116, 44, 113, 117, 97, 110, 116, 105,
                116, 121, 13, 10, 98, 97, 110, 97, 110, 97, 44, 49, 53, 50, 13, 10, 97, 112, 112,
                108, 101, 44, 57, 48};
        byte[] actual = fruitService.generateReport();
        assertEquals(expected.length, actual.length);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void makeReportAtTheEndOfTheDay_correctPath_ok() {
        assertTrue(fruitService.makeReportAtTheEndOfTheDay(CORRECT_PATH_FOR_INPUT,
                CORRECT_PATH_FOR_OUTPUT));
    }

    @Test
    public void makeReportAtTheEndOfTheDay_incorrectPathForInput_ok() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Can't read data from file");
        fruitService.makeReportAtTheEndOfTheDay(INCORRECT_PATH_FOR_INPUT, CORRECT_PATH_FOR_OUTPUT);
    }

    @Test
    public void makeReportAtTheEndOfTheDay_incorrectPathForOutput_ok() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Can't write data to file");
        fruitService.makeReportAtTheEndOfTheDay(CORRECT_PATH_FOR_INPUT, INCORRECT_PATH_FOR_OUTPUT);
    }

    @Test
    public void makeReportAtTheEndOfTheDay_incorrectPath_ok() {
        exceptionRule.expect(RuntimeException.class);
        fruitService.makeReportAtTheEndOfTheDay(INCORRECT_PATH_FOR_INPUT,
                INCORRECT_PATH_FOR_OUTPUT);
    }

    @After
    public void tearDown() {
        Storage.fruitList.clear();
    }
}
