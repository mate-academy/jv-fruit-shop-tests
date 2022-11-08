package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String PATH_DATA_FILE = "src/test/java/resources/dataTest.csv";
    private static final String PATH_EMPTY_FILE = "src/test/java/resources/emptyTest.csv";
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readFile_validData_ok() {
        final List<String> actual = fileReaderService.readFile(PATH_DATA_FILE);
        List<String> expected = new ArrayList<>();
        expected.add("testing");
        expected.add("readFile_Ok method,");
        expected.add("last line.");
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_emptyData_notOk() {
        List<String> actual = fileReaderService.readFile(PATH_EMPTY_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_pathIsInvalid_notOk() {
        List<String> actual = fileReaderService.readFile("src/test");
    }
}
