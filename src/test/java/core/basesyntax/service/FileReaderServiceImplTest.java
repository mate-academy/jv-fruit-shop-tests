package core.basesyntax.service;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String CORRECT_FILE_INPUT_PATH = "src/test/resources/input.csv";
    private static final String INCORRECT_FILE_INPUT_PATH = "src/test/resources/not-input.csv";
    private static FileReaderService fileReaderService;

    @Before
    public void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readExistingFile_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        List<String> actual = fileReaderService.read(CORRECT_FILE_INPUT_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void readNonexistentFile_NotOk() {
        fileReaderService.read(INCORRECT_FILE_INPUT_PATH);
    }
}
