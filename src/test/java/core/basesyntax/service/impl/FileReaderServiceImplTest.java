package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String CORRECT_PATH = "src/test/resources/correctInputFile.csv";
    private static final String WRONG_PATH = "src/test/resources/randomFile.csv";
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readFromFile_existingFile_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        List<String> actual = fileReaderService.readFromFile(CORRECT_PATH);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_notExistingFile_notOk() {
        fileReaderService.readFromFile(WRONG_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_nullFile_notOk() {
        fileReaderService.readFromFile(null);
    }
}
