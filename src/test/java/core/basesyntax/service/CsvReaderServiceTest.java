package core.basesyntax.service;

import core.basesyntax.service.impl.CsvReader;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvReaderServiceTest {
    private static final String CORRECT_EXAMPLE_FILE = "example.csv";
    private static final String NON_EXISTED_FILE = "";
    private static final List<String> CORRECT_EXAMPLE_LIST = List.of(
            "type,fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50");
    private static final int CORRECT_NUMBER_OF_SKIP_LINES = 3;
    private static final int NON_CORRECT_NUMBER_OF_SKIP_LINES = 100;
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeAll() {
        readerService = new CsvReader();
    }

    @Test
    public void read_skipCorrectNumberOfLines_ok() {
        List<String> expectedList = CORRECT_EXAMPLE_LIST.subList(
                CORRECT_NUMBER_OF_SKIP_LINES, CORRECT_EXAMPLE_LIST.size());
        List<String> actualList = readerService.readFile(CORRECT_EXAMPLE_FILE, CORRECT_NUMBER_OF_SKIP_LINES);
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void read_skipNonCorrectNumberOfLines_ok() {
        List<String> expected = List.of();
        List<String> actual = readerService.readFile(CORRECT_EXAMPLE_FILE, NON_CORRECT_NUMBER_OF_SKIP_LINES);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void read_readCorrectFile_ok() {
        List<String> actual = readerService.readFile(CORRECT_EXAMPLE_FILE);
        Assert.assertEquals(CORRECT_EXAMPLE_LIST, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_nonExistedFile_exception() {
        readerService.readFile(NON_EXISTED_FILE);
    }
}
