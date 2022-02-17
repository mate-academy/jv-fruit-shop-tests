package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DataWriterToFileTest {
    public static final String PATH_TO_REPORT_FILE = "src/test/resources/daily_report.csv";
    private static final String WRONG_PATH_TO_FILE = "1src/WRONG_PATH_TO_FILE";
    private static Map<Fruit, Integer> storage;
    private static Fruit banana;
    private static Fruit apple;
    private static Fruit cherry;
    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();
    private final DataWriterToFile writer = new DataWriterToFile();

    @BeforeClass
    public static void beforeClass() {
        storage = Storage.getStorageOfFruits();
        banana = new Fruit("banana");
        apple = new Fruit("apple");
        cherry = new Fruit("cherry");
    }

    @Before
    public void setUp() {
        storage.put(banana, 20);
        storage.put(apple, 30);
        storage.put(cherry, 40);
    }

    @Test
    public void writeData_validData_Ok() {
        writer.writeData(storage, PATH_TO_REPORT_FILE);
        List<String> actualResult;
        List<String> expectedResult;
        try {
            actualResult = Files.readAllLines(Path.of(PATH_TO_REPORT_FILE));
            expectedResult = List.of("fruit,quantity", "cherry,40", "banana,20", "apple,30");
        } catch (IOException e) {
            throw new RuntimeException("Cannot write to file by path: " + PATH_TO_REPORT_FILE, e);
        }
        assertNotNull(actualResult);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void writeData_wrongPath_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Cannot write to file by path: " + WRONG_PATH_TO_FILE);
        writer.writeData(storage, WRONG_PATH_TO_FILE);
    }

    @Test
    public void writeData_nullStorage_notOk() {
        exceptionRule.expect(NullPointerException.class);
        writer.writeData(null, PATH_TO_REPORT_FILE);
    }

    @After
    public void afterEachTest() {
        storage.clear();
    }
}
