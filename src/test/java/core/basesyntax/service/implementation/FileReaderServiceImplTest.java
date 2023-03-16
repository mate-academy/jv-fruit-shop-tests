package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.exeption.FruitShopException;
import org.junit.Before;
import org.junit.Test;
import java.util.List;


public class FileReaderServiceImplTest {
    private static final String PATH_TO_FILE_OK = "src/main/resources/FruitStorageOperation.csv";
    private static final String PATH_TO_EMPTY_FILE = "src/main/resources/EmptyFile.csv";
    private static final String INCORRECT_PATH = "F://incorrectName.csv";
    private static final String FIRST_LINE_IN_OK_FILE = "b,banana,20";
    private static final int INDEX_FOR_FIRST_LINE = 0;
    FileReaderServiceImpl fileReaderService;

    @Before
    public void setUp() {
        fileReaderService = new FileReaderServiceImpl();
        Storage.fruits.clear();
    }

    @Test
    public void readFromFile_ok() {
        List<String> linesFromFile = fileReaderService.readFromFile(PATH_TO_FILE_OK);
        String actual = linesFromFile.get(INDEX_FOR_FIRST_LINE);
        assertEquals(FIRST_LINE_IN_OK_FILE, actual);
    }

    @Test(expected = FruitShopException.class)
    public void readFromEmptyFile_notOk() {
        fileReaderService.readFromFile(PATH_TO_EMPTY_FILE);
        fail("Expected " + FruitShopException.class.getName() + " but it wasn't");
    }

    @Test(expected = FruitShopException.class)
    public void readFromNotFoundFile_notOk() {
        fileReaderService.readFromFile(INCORRECT_PATH);
        fail("Expected " + FruitShopException.class.getName() + " but it wasn't");
    }
}