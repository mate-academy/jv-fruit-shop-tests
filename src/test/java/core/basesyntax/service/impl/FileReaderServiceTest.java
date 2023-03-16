package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.FruitException;
import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceTest {
    private static final String CORRECT_NAME_FILE = "src/test/resources/reader_test.csv";
    private static final String INCORRECT_NAME_FILE = "src/test/resources/not_exist.csv";
    private static ReaderService readerService;
    private static List<String> expectedList;

    @BeforeClass
    public static void setUp() {
        readerService = new FileReaderService();
        expectedList = new ArrayList<>();
        expectedList.add("type,fruit,quantity");
        expectedList.add("b,banana,20");
        expectedList.add("b,apple,100");
        expectedList.add("s,banana,100");
        expectedList.add("p,banana,13");
        expectedList.add("r,apple,10");
        expectedList.add("p,apple,20");
        expectedList.add("p,banana,5");
        expectedList.add("s,banana,50");
    }

    @Test
    public void read_Ok() {
        List<String> actualList = readerService.read(CORRECT_NAME_FILE);
        assertEquals(expectedList, actualList);
    }

    @Test(expected = FruitException.class)
    public void read_NotOk_nullNameFile() {
        readerService.read(null);
    }

    @Test(expected = FruitException.class)
    public void read_NotOk_fileNameNotExist() {
        readerService.read(INCORRECT_NAME_FILE);
    }
}
