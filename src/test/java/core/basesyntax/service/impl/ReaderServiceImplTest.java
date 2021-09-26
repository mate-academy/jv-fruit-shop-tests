package core.basesyntax.service.impl;

import static org.junit.Assert.fail;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    public static final String EMPTY_FILE = "src/test/resources/empty_test.csv";
    public static final String INCORRECT_PATH = "src/test/some_file.txt";
    public static final String INPUT_FILE = "src/test/resources/input_test.csv";
    public static final String INFO_LINE = "operation,fruit,amount";
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFile_Ok() {
        List<String> expected = List.of("b,apple,80", "b,banana,60");
        List<String> actual = readerService.readFile(INPUT_FILE);
        Assert.assertEquals("File wasn't read correctly!", expected, actual);
    }

    @Test
    public void readFileEmptyFile_Ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = readerService.readFile(EMPTY_FILE);
        Assert.assertEquals("You should return empty list for empty data!", expected, actual);
    }

    @Test
    public void readFileRemovedInfoLine_Ok() {
        List<String> actual = readerService.readFile(INPUT_FILE);
        Assert.assertFalse("Result shouldn't contain info line!", actual.contains(INFO_LINE));
    }

    @Test
    public void readFileIncorrectPath_NotOk() {
        try {
            readerService.readFile(INCORRECT_PATH);
        } catch (RuntimeException e) {
            return;
        }
        fail("You should throw an exception for incorrect file path");
    }
}
