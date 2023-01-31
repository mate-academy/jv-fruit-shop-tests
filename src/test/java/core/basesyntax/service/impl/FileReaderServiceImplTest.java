package core.basesyntax.service.impl;

import core.basesyntax.service.FileReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    public static final String EMPTY_FILE = "src/test/resources/empty_test.csv";
    public static final String WRONG_PATH = "src/test/test_file.txt";
    public static final String INPUT_FILE_PATH = "src/test/resources/input_test.csv";
    public static final String HEADER = "operation,fruit,amount";
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void beforeAll() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readFromFile_Ok() {
        List<String> expected = List.of("b,apple,80", "b,banana,60");
        List<String> actual = fileReaderService.readFromFile(INPUT_FILE_PATH);
        Assert.assertEquals("File wasn't read correctly.", expected, actual);
    }

    @Test
    public void readFromFile_EmptyFile_Ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = fileReaderService.readFromFile(EMPTY_FILE);
        Assert.assertEquals("You should return empty list for empty data.", expected, actual);
    }

    @Test
    public void readFromFile_RemovedHeader_Ok() {
        List<String> actual = fileReaderService.readFromFile(INPUT_FILE_PATH);
        Assert.assertFalse("Result shouldn't contain info line.", actual.contains(HEADER));
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_WrongPath_NotOk() {
        fileReaderService.readFromFile(WRONG_PATH);
    }
}
