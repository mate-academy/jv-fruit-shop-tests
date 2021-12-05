package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WriterServiceImplTest {

    private static WriterService writerService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private final String wrongOutputFilename = "";
    private final String normalOutputFilename = "src/main/resources/reportTest.csv";

    @BeforeClass
    public static void beforeClass() throws Exception {
        FruitStorageDaoImpl fruitStorageDao = new FruitStorageDaoImpl();
        writerService = new WriterServiceImpl(fruitStorageDao);
    }

    @Before
    public void setUp() throws Exception {
        Storage.fruits.clear();
        Fruit orange = new Fruit("orange");
        orange.setQuantity(22);
        Fruit banana = new Fruit("banana");
        banana.setQuantity(35);
        Storage.fruits.add(orange);
        Storage.fruits.add(banana);
    }

    @Test
    public void writeDataToFile_IsTrue() {
        boolean actual = writerService.writeToFile(normalOutputFilename);
        boolean expected = true;
        assertEquals("Test failed! Result of writing to file should be " + expected
                + " but it is " + actual, expected, actual);
    }

    @Test
    public void writeToWrongNameFile_RuntimeException() {
        exceptionRule.expect(RuntimeException.class);
        writerService.writeToFile(wrongOutputFilename);
    }

    @Test
    public void writeDataToFile_ExceptionThrown() {
        String expectedMessage = "Can't write a new file";
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage(expectedMessage);
        writerService.writeToFile(wrongOutputFilename);
    }
}
