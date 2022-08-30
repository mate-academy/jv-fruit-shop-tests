package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String PATH_TO_EMPTY_FILE = "src/test/resources/EmptyFile.csv";
    private static final String PATH_TO_FILE = "src/test/resources/InputDataTest.csv";
    private static final String WRONG_PATH_TO_FILE = "src/test/resources/test.csv";
    private ReaderService readerService = new ReaderServiceImpl();

    @Test
    public void read_FileWithData_Ok() {
        List<String> fileLines = readerService.read(PATH_TO_FILE);
        int actual = fileLines.size();
        assertEquals("first_line_ok", fileLines.get(0));
        assertEquals("second_line_ok", fileLines.get(1));
        assertEquals(2, actual);
    }

    @Test
    public void read_EmptyFile_Ok() {
        List<String> fileLines = readerService.read(PATH_TO_EMPTY_FILE);
        int actual = fileLines.size();
        assertEquals(0, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_NoSuchFile_NotOk() {
        List<String> fileLines = readerService.read(WRONG_PATH_TO_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void read_filePathIsNull_NotOk() {
        List<String> fileLines = readerService.read(null);
    }
}
