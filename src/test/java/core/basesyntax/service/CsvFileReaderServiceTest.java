package core.basesyntax.service.imp;

import static org.junit.Assert.fail;

import core.basesyntax.exeption.FruitShopExeption;
import core.basesyntax.service.CsvFileReaderService;
import java.util.List;
import java.util.Objects;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileReaderServiceTest {
    private static final String FIRST_LINE = "b,banana,20";
    private static final String READ_FILE_NAME_NOTOK = "source_notok.csv";
    private static final String READ_FILE_NAME_OK = "source.csv";
    private static final String READ_FILE_NAME_EMPTY_NOTOK = "source_empty.csv";
    private static CsvFileReaderService csvFileReaderService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        csvFileReaderService = new CsvFileReaderService();
    }

    @Test(expected = FruitShopExeption.class)
    public void readFile_NotOk() {
        List<String> linesFromFile = csvFileReaderService.readFile(READ_FILE_NAME_NOTOK);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown for not existing file, but it wasn't");
    }

    @Test(expected = FruitShopExeption.class)
    public void readFile_Empty_NotOk() {
        List<String> linesFromFile = csvFileReaderService.readFile(READ_FILE_NAME_NOTOK);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown for empty file, but it wasn't");
    }

    @Test
    public void readFile_Ok() {
        List<String> linesFromFile = csvFileReaderService.readFile(READ_FILE_NAME_OK);
        String expected = FIRST_LINE;
        String actual = linesFromFile.get(0);
        if (!Objects.equals(expected, actual)) {
            fail("First line Expected: " + expected + ", but was: " + actual);
        }
    }
}

