package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.FileLinesReaderServiceImpl;
import java.util.Collections;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class FileLinesReaderServiceTest {
    private static final String TEST_DATA_FILE_PATH = "src/main/resources/test_data.txt";
    private static final String EMPTY_FILE_PATH = "src/main/resources/empty_file";
    private static final String NON_EXISTENCE_FILE_PATH = "this_file_not_exists";
    private static FileLinesReaderService fileLinesReaderService;

    @BeforeClass
    public static void beforeClass() {
        fileLinesReaderService = new FileLinesReaderServiceImpl();
    }

    @Test
    public void readFile_FileExistsHasContent_returnListOfLines_ok() {
        List<String> expectedLines = List.of("line 1", "line 2");
        List<String> actualLines = fileLinesReaderService.readFile(TEST_DATA_FILE_PATH);
        assertEquals("Test failed! Expected lines after reading file: "
                + expectedLines + ", but was: "
                + actualLines, expectedLines, actualLines);
    }

    @Test
    public void readFile_EmptyFile_returnEmptyList_ok() {
        List<String> expected = Collections.emptyList();
        List<String> actual = fileLinesReaderService.readFile(EMPTY_FILE_PATH);
        assertEquals("Test failed! Expected list agter reading empty file: "
                + expected
                + ", but was: " + actual, expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_FileNotExists_notOk() {
        fileLinesReaderService.readFile(NON_EXISTENCE_FILE_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_Null_notOk() {
        fileLinesReaderService.readFile(null);
    }

}
