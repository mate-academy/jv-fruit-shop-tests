package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.exception.FruitStoreException;
import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceTest {
    private static final String FIRST_LINE = "b,banana,21";
    private static final String FILE_NAME_NOT_OK = "input_any.csv";
    private static final String FILE_NAME_OK = "input.csv";
    private static final String FILE_NAME_EMPTY_NOT_OK = "input_empty.csv";
    private static ReaderServiceImpl readerService;

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
    }

    @Test(expected = FruitStoreException.class)
    public void readFile_FileNotFound_notOk() {
        readerService.readFileToList(FILE_NAME_NOT_OK);
    }

    @Test(expected = FruitStoreException.class)
    public void readFile_Empty_NotOk() {
        readerService.readFileToList(FILE_NAME_EMPTY_NOT_OK);
        fail("Expected " + FruitStoreException.class.getName()
                + " to be thrown for empty file, but it wasn't");
    }

    @Test
    public void readFile_Ok() {
        List<String> linesFromFile = readerService.readFileToList(FILE_NAME_OK);
        String expected = FIRST_LINE;
        String actual = linesFromFile.get(1);
        assertEquals(expected, actual);
    }
}
