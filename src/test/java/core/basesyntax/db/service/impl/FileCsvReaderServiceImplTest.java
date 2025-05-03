package core.basesyntax.db.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.service.FileCsvReaderService;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileCsvReaderServiceImplTest {
    private static final String VALID_FILE_PATH = "src/main/resources/input.csv";
    private static final String INVALID_FILE_PATH = "invalidFilePath";
    private static FileCsvReaderService fileCsvReaderService;

    @BeforeClass
    public static void setUp() {
        fileCsvReaderService = new FileCsvReaderServiceImpl();
    }

    @Test
    public void readFromFile_validFilePath_ok() {
        List<String> expected = List.of(
                "b,banana,321", "b,apple,110", "b,pineapple,34");
        List<String> actual = fileCsvReaderService.readFromFile(VALID_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void readFromNullPathFile_notOk() {
        fileCsvReaderService.readFromFile(null);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFileInvalidPath_notOk() {
        fileCsvReaderService.readFromFile(INVALID_FILE_PATH);
    }
}
