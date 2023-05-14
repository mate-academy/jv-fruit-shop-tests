package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceTest {
    private static final String CORRECT_FILE = "src/test/resources/correct.csv";
    private static final String EMPTY_FILE = "src/test/resources/empty.csv";
    private static final String NO_HEADER_FILE = "src/test/resources/withoutHeader.csv";
    private static final String UNEXISTING_FILE = "src/test/resources/unexisting.csv";
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_nullFile_notOk() {
        fileReaderService.readFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_unexistingFile_notOk() {
        fileReaderService.readFromFile(UNEXISTING_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_emptyFile_notOk() {
        fileReaderService.readFromFile(EMPTY_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_fileWithoutCsvHeader_notOk() {
        fileReaderService.readFromFile(NO_HEADER_FILE);
    }

    @Test
    public void readFromFile_fileWithCorrectData_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        List<String> actual = fileReaderService.readFromFile(CORRECT_FILE);
        assertEquals(expected, actual);
    }
}
