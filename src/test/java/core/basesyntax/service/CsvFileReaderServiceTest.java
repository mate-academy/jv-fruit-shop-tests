package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.exeption.FruitShopExeption;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileReaderServiceTest {
    private static final String FIRST_LINE = "b,banana,20";
    private static final String FILE_NAME_NOT_OK = "source_notok.csv";
    private static final String FILE_NAME_OK = "source.csv";
    private static final String FILE_NAME_EMPTY_NOT_OK = "source_empty.csv";
    private static CsvFileReaderService csvFileReaderService;

    @BeforeClass
    public static void beforeClass() {
        csvFileReaderService = new CsvFileReaderService();
    }

    @Test(expected = FruitShopExeption.class)
    public void readFile_FileNotFound_notOk() {
        csvFileReaderService.readFile(FILE_NAME_NOT_OK);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown for not existing file, but it wasn't");
    }

    @Test(expected = FruitShopExeption.class)
    public void readFile_Empty_NotOk() {
        csvFileReaderService.readFile(FILE_NAME_EMPTY_NOT_OK);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown for empty file, but it wasn't");
    }

    @Test
    public void readFile_Ok() {
        List<String> linesFromFile = csvFileReaderService.readFile(FILE_NAME_OK);
        String expected = FIRST_LINE;
        String actual = linesFromFile.get(0);
        assertEquals("First line Expected: " + expected + ", but was: " + actual, expected, actual);
    }
}

