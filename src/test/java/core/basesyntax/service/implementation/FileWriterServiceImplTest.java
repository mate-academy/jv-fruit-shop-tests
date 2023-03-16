package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.exeption.FruitShopException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String INCORRECT_PATH = "F://incorrectPath.csv";
    private static final String PATH_TO_FILE_OK = "src/main/resources/Report.csv";
    private static final String FRUIT_FOR_STORAGE = "banana";
    private static final Integer FRUIT_QUANTITY_FOR_STORAGE = 50;
    private static final String FIRST_LINE = "fruit,quantity";
    private static final String SECOND_LINE = "banana,50";
    private FileWriterServiceImpl fileWriterService;

    @Before
    public void setUp() {
        fileWriterService = new FileWriterServiceImpl();
        Storage.fruits.clear();
    }

    @Test(expected = FruitShopException.class)
    public void writeToFile_incorrectPath_notOk() {
        Storage.put(FRUIT_FOR_STORAGE, FRUIT_QUANTITY_FOR_STORAGE);
        fileWriterService.writeToFile(INCORRECT_PATH, FIRST_LINE);
        fail("Expected " + FruitShopException.class.getName() + " but wasn't!");
    }

    @Test
    public void writeToFile_ok() {
        String message = FIRST_LINE + System.lineSeparator() + SECOND_LINE;
        Storage.fruits.put(FRUIT_FOR_STORAGE, FRUIT_QUANTITY_FOR_STORAGE);
        fileWriterService.writeToFile(PATH_TO_FILE_OK, message);

        File file = new File(PATH_TO_FILE_OK);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();
            assertEquals("Head line is incorrect " + line, line, FIRST_LINE);

            line = bufferedReader.readLine();
            assertEquals("Data in second line is incorrect", line, SECOND_LINE);

        } catch (IOException e) {
            throw new FruitShopException("Can't read from file " + file);
        }
    }
}
