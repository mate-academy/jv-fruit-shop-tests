package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WriterServiceImplTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private WriterService writerService;
    private final String wrongOutputFilename = "///";
    private final String normalOutputFilename = "src/main/resources/reportTest.csv";

    @Before
    public void setUp() throws Exception {
        FruitStorageDaoImpl fruitStorageDao = new FruitStorageDaoImpl();
        writerService = new WriterServiceImpl(fruitStorageDao);

        Storage.fruits.clear();
        Fruit orange = new Fruit("orange");
        orange.setQuantity(22);
        Fruit banana = new Fruit("banana");
        banana.setQuantity(35);
        Storage.fruits.add(orange);
        Storage.fruits.add(banana);
    }

    @Test(expected = RuntimeException.class)
    public void writeToWrongNameFile_RuntimeException() {
        writerService.writeToFile(wrongOutputFilename);
    }

    @Test
    public void writeDataToFile_IsTrue() {
        boolean actual = writerService.writeToFile(normalOutputFilename);
        boolean expected = true;
        assertEquals("Test failed! Result of writing to file should be " + expected
                + " but it is " + actual, expected, actual);
    }

    @Test
    public void writeDataToFile_ExceptionThrown() {
        exceptionRule.expect(RuntimeException.class);
        String expectedMessage = "Can't write a new file";
        exceptionRule.expectMessage(expectedMessage);
        writerService.writeToFile(wrongOutputFilename);
    }
}
